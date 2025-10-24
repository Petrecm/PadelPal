# PadelPal – **The Smart Padel Court Booking & Coaching Platform**

---

## Team Members
- Petrec Matei-Teodor
- Bontas Andrian-Cosmin

---

## Project Description
PadelPal is a modern, implementable **Padel Court Booking and Coaching Management System** designed to bring digital innovation to club operations.  
The platform offers **real-time scheduling, dynamic pricing, membership management, and IoT integration** for lighting and access control.

PadelPal supports multiple **user roles** → *Players*, *Members*, *Coaches*, *Managers*, and *Maintenance Staff* — each with distinct privileges and workflows.  
The system ensures efficient court utilization, accurate billing, and seamless coordination between players and club staff.

PadelPal promotes **scalability**, **maintainability**, and **extensibility**, enabling future integrations such as AI-based training recommendations, wearable analytics, or predictive court maintenance.

---

## Core Modules & Features

### **1. User Categories & Responsibilities**
- **Player:** Book courts, join or host matches, split payments, and view match history.  
- **Member:** Enjoy discounted rates, early booking access, and loyalty rewards.  
- **Coach:** Organize training sessions, manage participants, record progress, and provide digital feedback.  
- **Club Manager:** Configure pricing rules, oversee booking schedules, approve refunds, and monitor financial reports.  
- **Maintenance Crew:** Receive *auto-generated alerts* for cleaning or repair tasks; update court availability.  
- **Administrator:** Full system access for CRUD operations on users, courts, and pricing models; manage role permissions.

---

### **2. Court & Schedule Management**
- Courts include **metadata** such as *surface type, location, indoor/outdoor flag, lighting system,* and *availability status.*  
- Dynamic **calendar view** displaying real-time bookings and maintenance windows.  
- **Double-booking prevention** via transactional locks.  
- **IoT Integration:** automatic light and door activation during reserved time slots.  
- Maintenance thresholds trigger alerts when courts exceed configured usage hours.

---

### **3. Booking Lifecycle & Match Workflow**
- **Lifecycle:** *Requested* ➜ *Confirmed* ➜ *In-Progress* ➜ *Completed* ➜ *Cancelled*.  
- **Validation:** prevents overlapping or expired reservations.  
- **Cancellation Policies:** configured per club; refund eligibility validated automatically.  
- **Split Payments:** participants can share booking costs dynamically.  
- **Matchmaking:** suggests opponents or partners based on skill level and time preference.  
- **Coaching Sessions:** booked directly through coach schedules with linked courts.

---

### **4. Dynamic Pricing & Membership Plans**
**Pricing Strategies (Strategy Pattern):**
- `PeakHourStrategy` → adjusts cost during high-demand hours.  
- `MemberDiscountStrategy` → applies tier-based discounts.  
- `WeatherStrategy` → modifies price for outdoor courts during adverse conditions.  
- `EventStrategy` → special pricing during tournaments or club events.

**Advantages:**  
- Eliminates complex conditional logic.  
- Enables new rules without modifying core code.  
- Encourages **Open/Closed Principle** compliance.

---

### **5. Coaching & Performance Tracking**
- Coaches define **structured sessions**: skill level, duration, capacity, and recurrence.  
- Participants receive **automated reminders** and feedback summaries post-session.  
- Historical performance stored per player for progress analysis.  
- *Observer Pattern* notifies analytics modules upon session completion.  
- Optional integration with wearables (e.g., smartwatch data).

---

### **6. Notification & Event System**
**Event Flow (Observer Pattern):**
- **BookingConfirmedEvent:** notifies players, updates court status, triggers IoT lighting.  
- **SessionEndedEvent:** sends feedback forms, updates attendance logs.  
- **CourtMaintenanceEvent:** alerts maintenance team, blocks court availability.  
- **PaymentCompletedEvent:** sends invoices and confirmation messages.

**Benefits:**  
- *Loose coupling* between modules.  
- Simple addition of new event subscribers (CRM, analytics, IoT).  
- Promotes asynchronous and scalable workflows.

---

### **7. Analytics & Reporting Dashboard**
- Visual dashboards for **occupancy, revenue, and player activity.**  
- Track **court utilization by hour/day** and membership tier usage.  
- Evaluate **coach performance** and session popularity.  
- Exportable **CSV/PDF reports** for management review.  
- Integrates with external BI tools (optional future milestone).

---

## Design Patterns & Justifications

1. **Strategy Pattern**  
   - **Problem:** Pricing varies dynamically by context (member type, time, event).  
   - **Solution:** Define `PricingStrategy` interface with concrete implementations for each scenario.  
   - **Justification:** Enables flexible, modular pricing without rewriting core booking logic.  
   - **Advantage:** Easy to extend, improves maintainability, aligns with SOLID principles.

2. **State Pattern**  
   - **Problem:** Bookings transition between multiple stages, each requiring unique behavior.  
   - **Solution:** Implement separate `BookingState` classes (`RequestedState`, `ConfirmedState`, etc.).  
   - **Justification:** Avoids messy conditional statements, ensures valid transitions only.  
   - **Advantage:** Clear, debuggable lifecycle; simplifies rollback or undo operations.

3. **Command Pattern**  
   - **Problem:** Critical actions (booking, cancel, refund) require audit logging, retries, and potential undo.  
   - **Solution:** Encapsulate actions as `Command` objects executed by a `CommandHandler`.  
   - **Justification:** Centralizes business logic, supports transaction safety.  
   - **Advantage:** Improves traceability and enables asynchronous command processing.

4. **Observer Pattern**  
   - **Problem:** Multiple services (notifications, IoT, analytics) must react to domain events.  
   - **Solution:** Use event dispatchers and observers to handle asynchronous updates.  
   - **Justification:** Promotes modular, event-driven design; no hard dependencies.  
   - **Advantage:** High scalability, easy integration of new reactive modules.

5. **Facade Pattern**  
   - **Problem:** Different roles (coach, player, staff) interact with distinct subsystems.  
   - **Solution:** Create dedicated facades: `CoachFacade`, `PlayerFacade`, `MaintenanceFacade`.  
   - **Justification:** Simplifies user operations and hides internal system complexity.  
   - **Advantage:** Enhances usability, security, and separation of concerns.

---

## Expected Benefits
- **Realism:** Mirrors true padel club workflows — bookings, memberships, coaching, and maintenance.  
- **Extensibility:** Add new pricing models, user roles, or IoT devices easily.  
- **Maintainability:** Each pattern isolates logic for simplified future development.  
- **Scalability:** Supports multiple clubs, large user bases, and event-driven concurrency.  
- **Reliability:** State machine guarantees booking integrity; event system ensures synchronization.

---

## Conclusion
PadelPal provides a **robust, extensible, and realistic** solution for managing padel club operations.  
Through the use of **Strategy**, **State**, **Command**, **Observer**, and **Facade** patterns, the system achieves modularity, maintainability, and professional-grade scalability.  
It demonstrates practical application of software engineering principles, ready for both **academic evaluation** and **real-world adaptation.**
