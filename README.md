# PadelPal – The Smart Padel Court Booking & Coaching Platform | Milestone 2

---

## Team Members
- Petrec Matei-Teodor  
- Bontaș Andrian-Cosmin  

---

## Project Description
PadelPal is a modular platform for managing padel clubs, integrating booking, coaching, and maintenance functionalities.  
This milestone focuses on the system’s design and proof-of-concept implementation, emphasizing **clean architecture** and the use of key design patterns:  
Factory, Strategy, State, Facade, Decorator, Observer, and Command.  

The goal is to showcase a maintainable and extensible architecture where each pattern contributes to flexibility, reusability, and cohesion.

---

## Project Structure and Layers

### 1. Court & Pricing Management (Factory + Strategy)
Defines the creation and pricing logic for different padel court types. Each court’s cost dynamically adapts based on contextual factors such as peak hours, membership, or weather.

**Key Components:**
- CourtFactory — creates court objects (`IndoorCourt`, `OutdoorCourt`, `PremiumCourt`).  
- Pricing Strategies (`PricingStrategy`):  
  - `BasePricing` – standard fixed rate.  
  - `PeakHourStrategy` – optional dynamic pricing during high demand.  
  - `MemberDiscountStrategy` – applies loyalty discounts.  
  - `WeatherStrategy` – discounts outdoor courts in poor weather.  
  - `EventStrategy` – adds surcharges for special events.

**Example Rates:**

| Court Type | Rate (lei/hour) |
|-------------|----------------|
| IndoorCourt | 80 |
| OutdoorCourt | 60 |
| PremiumCourt | 100 |

**Example Usage:**
```java
Court court = new CourtFactory().create("Premium");
PricingStrategy pricing = new EventStrategy(
    new WeatherStrategy(
        new PeakHourStrategy(
            new MemberDiscountStrategy(
                new BasePricing()
            )
        )
    )
);
double finalPrice = pricing.calculatePrice(court, 2);
System.out.println("Final price: " + finalPrice + " lei");
```

**Purpose:** Combines Factory and Strategy to make court management flexible, configurable, and reusable.

---

### 2. Booking Workflow (State + Facade)
Manages reservation creation, confirmation, and progression through defined lifecycle states, while exposing a simple interface for end users.

**Key Components:**
- Booking — core entity linking players, courts, and payments.  
- BookingState Interface — defines valid transitions.  
- States: `Requested`, `Confirmed`, `InProgress`, `Completed`, `Cancelled`.  
- BookingFacade — central interface for actions like `createAndPrice`, `confirmBooking`, and `cancelBooking`.

**Example Usage:**
```java
BookingFacade bookingFacade = new BookingFacade(new CourtFactory(), pricing, new EventPublisher());
var booking = bookingFacade.createAndPrice("Premium", "B-2001", 2, true);
System.out.println(booking);
booking.confirm();
booking.complete();
```

**Purpose:** Combines State and Facade to maintain valid booking transitions and simplify user operations.

---

### 3. Coaching System (Decorator + Facade)
Allows coaches to design flexible training sessions and attach additional features such as recording or equipment rental.

**Key Components:**
- TrainingSessionComponent — abstract session type.  
- TrainingSession — core implementation.  
- Decorators:  
  - `VideoRecordingDecorator` – records sessions.  
  - `EquipmentRentalDecorator` – includes equipment in booking.  
- CoachFacade — manages scheduling and reporting.

**Example Usage:**
```java
CoachFacade coachFacade = new CoachFacade();
var session = coachFacade.buildSession("Coach Alex", 90, true, true);
System.out.println(session.description());
System.out.println("Cost: " + session.cost() + " lei");
```

**Purpose:** Combines Decorator and Facade for modular, extensible training management.

---

### 4. Event System (Observer)
Coordinates communication between system modules (notifications, IoT, analytics) through asynchronous event updates.

**Key Components:**
- EventPublisher — dispatches system events.  
- Observers:  
  - BillingService — issues invoices automatically.  
  - NotificationService — sends messages to players.  
  - IoTService — handles lighting and access gates.  
  - AnalyticsService — updates performance dashboards.

**Example Flow:**
```java
EventPublisher publisher = new EventPublisher();
publisher.register(new NotificationService());
publisher.register(new IoTService());
publisher.register(new AnalyticsService());
publisher.publish("Booking B-2001 completed");
```

**Purpose:** Uses Observer to decouple services, ensuring smooth updates across independent subsystems.

---

### 5. Command Layer (Command Pattern)
Centralizes all high-level user operations — such as booking creation and cancellation — into executable commands.  
This ensures a clear execution flow, logging, and the ability to extend functionality without altering existing code.

**Key Components:**
- Command Interface — defines an executable action (`execute()`).  
- Concrete Commands:  
  - `CreateBookingCommand` – initializes and registers a booking.  
  - `CancelBookingCommand` – cancels and logs refunds or updates.  
- CommandBus — processes commands sequentially and records them for auditing.

**Example Usage:**
```java
CommandBus bus = new CommandBus();
bus.execute(new CreateBookingCommand(booking));
bus.execute(new CancelBookingCommand(booking));
```

**Purpose:** Implements Command to centralize and trace critical operations, enhancing maintainability and control.

---

## Design Patterns Demonstrated

1. **Factory** — `CourtFactory` creates different court types with preset configurations.  
2. **Strategy** — `PricingStrategy` allows flexible and extensible pricing calculation.  
3. **State** — controls the booking lifecycle (Requested → Confirmed → InProgress → Completed → Cancelled).  
4. **Decorator** — enables dynamic add-ons such as `VideoRecordingDecorator` and `EquipmentRentalDecorator`.  
5. **Facade** — simplifies interactions with subsystems like booking, coaching, and pricing.  
6. **Observer** — triggers asynchronous updates across notification, IoT, and analytics services.  
7. **Command** — encapsulates major user actions like booking creation or cancellation.

---

## Milestone 2 Scope

- Implemented **court and pricing logic** using Factory and Strategy patterns.  
- Implemented **booking lifecycle management** using the State pattern.  
- Implemented **facades** for booking and coaching operations.  
- Implemented **decorator-based add-ons** for training session customization.  
- Implemented **observer pattern** for notifications and automated IoT updates.  
- Implemented **command layer** to execute and log booking-related operations.  
- Delivered a **proof-of-concept demonstration** for cost calculation, booking creation, and event-driven updates.

---
