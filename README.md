# Milestone 3: PadelPal Architectural Analysis

**Project:** PadelPal – Padel Court Booking & Coaching Platform  
**Goal:** Investigate, describe, and evaluate three different software architectures – **Monolithic**, **Microservices**, and **Event-Driven Architecture (EDA)** – tailored to PadelPal’s needs (scalability, extensibility, IoT integration, notifications, analytics).

## Team
- Petrec Matei-Teodor  
- Bontaș Andrian-Cosmin  

---

## 1. Monolithic Architecture

The monolithic version of **PadelPal** bundles all platform functionality—bookings, court scheduling, pricing, coaching, notifications, analytics, and IoT—into a **single deployable application** backed by one unified database. All modules execute in the same process and share the same relational schema.

### A. Structure Description

| Component | Description & Role | Interactions & Data Flow |
| :--- | :--- | :--- |
| **PadelPal Monolith Application** | Contains all business logic: booking lifecycle, court availability, pricing, coaching sessions, notifications, analytics, IoT control. Internal patterns (State, Strategy, Decorator, Command, Factory, Observer) are implemented as modules inside this single app. | Controllers receive HTTP requests from the web / mobile client and call internal facades/services via **in-process method calls**. |
| **Unified PadelPal Database** | Single relational database instance (e.g. MariaDB/PostgreSQL) used by all modules. | Stores users, courts, bookings, coaching sessions, pricing rules, notifications, analytics snapshots, IoT logs in one schema, enabling **ACID transactions** across modules. |

### B. Diagrams

#### Component Diagram
![Monolithic Component Diagram](monolithic_component.png)

#### Deployment Diagram
![Monolithic Deployment Diagram](monolithic_deployment.png)

### C. Pros and Cons

| Advantages | Disadvantages |
| :--- | :--- |
| **Simple development & deployment** – One codebase, one artifact, one database. | **Low scalability** – To handle more traffic, the entire application must be scaled together. |
| **Strong consistency** – Cross-cutting operations (booking + billing + notification) can be wrapped in a single transaction. | **Tight coupling** – Modules depend heavily on each other; changes in one area can impact others. |
| **Easy debugging & onboarding** – All logic in one place, simpler for a small team/university project. | **Limited fault isolation** – A bug in analytics or IoT can crash the whole platform. |
| **No network overhead between modules** – All calls are in-process. | **Slower evolution over time** – Adding new features (extra services, more IoT, more analytics) makes the monolith harder to maintain and deploy. |

---

## 2. Microservices Architecture

In the **microservices** architecture, PadelPal is decomposed into several independent services based on business domains. Each service runs in its own process/container and owns its own database. Communication between services is primarily done via REST APIs, with the option to emit simple domain events.

### A. Structure Description

We derive the services from the existing patterns and flows in the codebase:

| Component | Description & Role | Interactions & Data Flow |
| :--- | :--- | :--- |
| **Booking Service** | Core booking workflow: hosts `BookingFacade`, the State pattern (Requested → Confirmed → InProgress → Completed → Cancelled), the Command pattern, and `CourtFactory`. Owns **BookingDB**. | Receives booking-related HTTP requests (via API Gateway). Reads/writes bookings in its own DB. Can call Court & Schedule and Pricing services via REST for availability and price calculation. |
| **Court & Schedule Service** | Manages courts, slots, maintenance windows. Owns **CourtDB**. | Exposes endpoints to check and update court availability. |
| **Pricing Service** | Encapsulates the pricing Strategy chain (BasePricing → MemberDiscount → PeakHour → Weather → Event). Owns **PricingDB** with pricing rules. | Exposes endpoints to calculate final prices for a given booking request. |
| **User & Auth Service** | Manages players, coaches, managers and authentication. Owns **UserDB**. | Handles login, registration, and role checks for other services. |
| **Coaching Service** | Manages coaching sessions and training add-ons using decorators. Owns **CoachingDB**. | Exposes APIs for booking coaching sessions and retrieving coach schedules. |
| **Notification Service** | Sends emails/push notifications (confirmation, reminders, cancellations). Owns **NotificationDB**. | Receives REST calls or simple events from Booking Service and records sent notifications. |
| **Analytics Service** | Aggregates booking and revenue statistics, writing to **AnalyticsDB**. | Periodically reads data from other services (or their events) and stores aggregates. |
| **IoT Adapter Service** | Talks to physical devices (lights, door locks) for bookings. Owns **IoTDB** (or shares some court metadata). | Receives calls from Booking Service to turn devices on/off at booking start/end. |

An **API Gateway** sits in front of these services, acting as the single entry point for the web/mobile client.

### B. Diagrams

#### Component Diagram
![Microservices Component Diagram](microservices_component.png)

#### Deployment Diagram
![Microservices Deployment Diagram](microservices_deployment.png)

### C. Pros and Cons

| Advantages | Disadvantages |
| :--- | :--- |
| **Independent scaling** – Booking and Notification services can be scaled up without touching Coaching or Analytics. | **Operational complexity** – Many services to configure, deploy, monitor, and secure. |
| **Better fault isolation** – Failure in Analytics or IoT does not necessarily stop core booking flows. | **Distributed data** – No single ACID transaction across services; consistency requires patterns such as Sagas or compensating actions. |
| **Technology flexibility** – Services can use different stacks (e.g. Java for Booking, Python for Analytics). | **Higher latency & more failure points** – Cross-service communication is over the network, which introduces latency and potential timeouts. |
| **Independent deployments** – One service can be updated without redeploying the whole system. | **Higher observability requirements** – Needs centralized logging, metrics, and distributed tracing. |

---

## 3. Event-Driven Architecture (EDA)

The **event-driven** architecture builds on top of the microservices decomposition and uses **asynchronous events** as the main form of communication for cross-cutting concerns. This matches the Observer pattern already used in PadelPal, where an `EventPublisher` notifies multiple observers (Billing, Notifications, Analytics, IoT) when something happens in the booking lifecycle.

### A. Structure Description

| Component | Description & Role | Interactions & Data Flow |
| :--- | :--- | :--- |
| **Event Producers** | Mainly the **Booking Service** (and optionally User & Auth). Emits domain events such as `BookingCreated`, `BookingApproved`, `BookingCompleted`, `BookingCancelled`. | After updating its own database, the producer publishes events to the Message Broker and does not wait for consumers to finish. |
| **Message Broker** | Middleware such as RabbitMQ or Kafka that routes events to interested consumers. | Buffers events, implements topics/queues (e.g. `BookingEvents`) and delivers messages to consumer services. |
| **Event Consumers** | Services that react to events: Notification, Billing (optional), Analytics, IoT. | Each consumer subscribes to the relevant event types and updates its own database or triggers external side effects. |

Typical event flow for a completed booking:

1. User confirms a booking via the Booking Service API.  
2. Booking Service changes state to `Completed`, writes to **BookingDB**, and publishes `BookingCompleted`.  
3. Notification Service receives the event, sends a confirmation email, and logs it in **NotificationDB**.  
4. Analytics Service updates statistics in **AnalyticsDB**.  
5. IoT Adapter Service turns off the court lights and logs the action in **IoTDB**.  

### B. Diagrams

#### Component Diagram
![EDA Component Diagram](eventdriven_component.png)

#### Deployment Diagram
![EDA Deployment Diagram](eventdriven_deployment.png)

### C. Pros and Cons

| Advantages | Disadvantages |
| :--- | :--- |
| **Extreme decoupling** – Producers do not know who consumes their events; new services can be added by simply subscribing to topics. | **Eventual consistency** – Data in consumer services is updated asynchronously, not in a single transaction. |
| **Great fit for notifications, analytics, and IoT** – These concerns are naturally event-based. | **Harder debugging** – Tracing a user action across multiple asynchronous events requires good logging/tracing. |
| **Scalable and resilient** – If a consumer is down, events queue up in the broker instead of blocking bookings. | **Infrastructure overhead** – Requires a highly available message broker and additional configuration. |
| **Easy extensibility** – New features (e.g. loyalty, external partners) can plug into existing events. | **Event schema evolution** – Events must be versioned and kept backward-compatible. |

---

## 4. Final Comparison and Conclusion

### A. Comparison of Architectures

| Feature | Monolithic | Microservices | Event-Driven (EDA) |
| :--- | :--- | :--- | :--- |
| **Scalability** | Low – must scale entire app. | High – services scale independently. | Very high – producers and consumers scale separately via the broker. |
| **Complexity (implementation & ops)** | Low | Medium | High |
| **Data consistency** | Strong (single DB, ACID). | Mixed; needs explicit patterns. | Eventual consistency by design. |
| **Fault isolation** | Poor – one bug may crash everything. | Good – failures limited to one service. | Excellent – broker decouples producers and consumers. |
| **Fit for IoT & real-time notifications** | Possible but tightly coupled. | Good – dedicated services. | Excellent – events drive IoT and notifications naturally. |
| **Extensibility (adding new features)** | Low – large, coupled codebase. | Medium – can add new services, but must integrate APIs. | High – new consumers can subscribe to existing events. |

### B. Final Selection and Justification

The most suitable long-term architecture for **PadelPal** is the **Event-Driven Architecture built on a microservices foundation**.

**Reasons:**

1. **Natural mapping to existing design**  
   The current implementation already uses an Observer-style `EventPublisher` that notifies Billing, Notification, Analytics and IoT components. Turning this into real distributed events via a Message Broker is an incremental and realistic step.

2. **Support for growth and peak usage**  
   As more clubs, courts, and players join PadelPal, bookings and notifications will spike at certain hours. Microservices allow independent scaling of Booking and Notification services, while EDA ensures that heavy consumers (Analytics, IoT) do not slow down core booking flows.

3. **IoT and real-time extensions**  
   Lights and access control are naturally event-driven: “booking started” → turn lights on, “booking completed” → turn lights off. EDA models this directly and cleanly.

4. **Extensibility for future features**  
   New services (e.g. loyalty points, partner integrations, recommendation engines) can simply subscribe to existing booking and user events without changing the core booking API.

In practice, PadelPal can **start as a monolith** (as implemented in earlier milestones), then gradually **extract services** and introduce **events** where needed. This evolution path offers a realistic, technically sound architecture that fits both the current academic project and a potential real-world product.
