# ATM Cash Withdrawal – LLD (Interview Revision Guide)

This document explains the **ATM Cash Withdrawal system design** using:
- Chain of Responsibility
- Template Method
- Enum
- Facade
- Thread Safety

It is written for **LLD interviews** (SDE-1 / SDE-2).

---

## 1. Problem Statement

Design an ATM system that:
- Dispenses cash using denominations: 2000, 500, 100
- Prefers **higher denominations first**
- Supports **limited inventory per denomination**
- Allows **partial withdrawal** (best-effort)
- Is **thread-safe**
- Avoids `if-else` chains

---

## 2. Core Insight (MOST IMPORTANT)

> Cash withdrawal is a **step-by-step transformation** of amount.

Example:
3700
↓
2000 → remainder 1700
500 → remainder 200
100 → remainder 0


This maps **perfectly** to **Chain of Responsibility**.

---

## 3. Why Chain of Responsibility?

- Multiple handlers process the **same request**
- Each handler:
    - Does partial work
    - Forwards remainder

ATM → 2000Handler → 500Handler → 100Handler


❌ Strategy is NOT suitable (only one handler runs)

---

## 4. Denomination Enum

```java
public enum Denomination {
    TWO_THOUSAND(2000),
    FIVE_HUNDRED(500),
    ONE_HUNDRED(100);

    private final int value;

    Denomination(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
✔ Type-safe
✔ Extensible
✔ No magic numbers

5. Inventory Model (State Ownership)
Inventory is mutable domain state, so it belongs to ATM, NOT handlers.

public class CashInventory {

    private final Map<Denomination, Integer> inventory = new EnumMap<>(Denomination.class);

    public void add(Denomination d, int count) {
        inventory.put(d, count);
    }

    public int getAvailable(Denomination d) {
        return inventory.getOrDefault(d, 0);
    }

    public void deduct(Denomination d, int count) {
        inventory.put(d, inventory.get(d) - count);
    }
}
6. ATMHandler (Chain + Template Method)
Handlers are:

Stateless

Reusable

Algorithm shared in base class

public abstract class ATMHandler {

    protected ATMHandler next;
    protected final Denomination denomination;

    protected ATMHandler(ATMHandler next, Denomination denomination) {
        this.next = next;
        this.denomination = denomination;
    }

    // Returns remaining amount (supports partial withdrawal)
    public int processAmount(int amount, CashInventory inventory) {

        int value = denomination.getValue();
        int available = inventory.getAvailable(denomination);

        int required = amount / value;
        int toDispense = Math.min(required, available);

        if (toDispense > 0) {
            inventory.deduct(denomination, toDispense);
            System.out.println(value + " x " + toDispense);
        }

        int remainder = amount - (toDispense * value);

        if (remainder > 0 && next != null) {
            return next.processAmount(remainder, inventory);
        }

        return remainder;
    }
}
✔ No duplication
✔ Template Method applied
✔ Chain intact

7. Concrete Handlers (Very Thin)
public class TwoThousandHandler extends ATMHandler {
    public TwoThousandHandler(ATMHandler next) {
        super(next, Denomination.TWO_THOUSAND);
    }
}
(Same pattern for 500 and 100.)

8. ATM Facade (System Boundary)
ATM:

Owns inventory

Builds handler chain

Controls concurrency

public class ATM {

    private final ATMHandler chain;
    private final CashInventory inventory;

    public ATM() {
        ATMHandler hundred = new OneHundredHandler(null);
        ATMHandler fiveHundred = new FiveHundredHandler(hundred);
        ATMHandler twoThousand = new TwoThousandHandler(fiveHundred);

        this.chain = twoThousand;

        inventory = new CashInventory();
        inventory.add(Denomination.TWO_THOUSAND, 5);
        inventory.add(Denomination.FIVE_HUNDRED, 10);
        inventory.add(Denomination.ONE_HUNDRED, 20);
    }

    // THREAD SAFE
    public synchronized void withdraw(int amount) {

        if (amount <= 0 || amount % 100 != 0) {
            throw new IllegalArgumentException("Invalid amount");
        }

        int remaining = chain.processAmount(amount, inventory);
        int dispensed = amount - remaining;

        System.out.println(
            "Requested=" + amount +
            ", Dispensed=" + dispensed +
            ", Remaining=" + remaining
        );
    }
}
9. Partial Withdrawal Behavior
If full amount cannot be dispensed:

Dispense maximum possible

Do NOT throw exception

Report remaining amount

Example:

Inventory:
2000 → 1
500  → 1
100  → 1

withdraw(3700)
Output:

2000 x 1
500 x 1
100 x 1
Requested=3700, Dispensed=2600, Remaining=1100
10. Thread Safety Design (INTERVIEW CRITICAL)
What is synchronized?
✔ ATM.withdraw()

Why?
ATM represents one physical machine

Inventory is shared mutable state

Handlers are stateless

Why NOT synchronize handlers?
Over-locking

Hard to reason

Wrong abstraction

Lock at the system boundary, not inside components

11. Should ATM be Singleton?
❌ NOT always.

If system manages multiple ATMs → NOT Singleton

If modeling one physical ATM → Can be Singleton

Singleton is a domain decision, not a reflex.

12. Patterns Used (Mention in Interview)
Pattern	Purpose
Chain of Responsibility	Sequential cash dispensing
Template Method	Shared algorithm
Facade	Clean client API
Enum	Safe denominations
Synchronization	Thread safety
13. Interview One-Liners (MEMORIZE)
“ATM withdrawal is a Chain of Responsibility problem.”

“Handlers are stateless; ATM owns inventory.”

“Partial withdrawal returns remaining amount.”

“Thread safety is enforced at ATM boundary.”

“Patterns solve structure, not business rules.”

14. Mental Model (FINAL)
Client Threads
   ↓
synchronized ATM.withdraw()
   ↓
2000 → 500 → 100 handlers
   ↓
Inventory updated safely
15. Possible Follow-Ups (If Asked)
Rollback on failure (two-phase commit)

Limited notes per denomination

Receipt / audit trail

Distributed ATM system

Fair locking

⭐ Final Takeaway
Good LLD changes behavior without changing architecture.

Revise this once before interview — you’re fully prepared to explain, code, and defend this design.


---

If you want next:
- **1-minute whiteboard explanation**
- **Mock interview (pressure round)**
- **Another CoR-based LLD question**
- **Compare ATM vs Logger mapping**

Just tell me 👍
