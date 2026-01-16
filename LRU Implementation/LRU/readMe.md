# LRU Cache – Java Implementation

## Overview

LRU (Least Recently Used) Cache is an in-memory cache eviction strategy that removes the **least recently accessed entry** when the cache reaches its capacity.

This implementation guarantees:
- **O(1)** time complexity for `get()` and `put()`
- Deterministic eviction
- Thread safety (optional version)

---

## Key Concepts

- **LRU Policy**:  
  The entry that has not been accessed for the longest time is evicted first.
- **Access = get() or put()**
- Eviction happens **only on put()**, never on get()

---

## Functional Requirements

- Retrieve value by key in **O(1)**
- Insert or update value in **O(1)**
- Maintain access order
- Evict least recently used entry when capacity exceeds

---

## Non-Functional Requirements

- Time Complexity: `O(1)` for all operations
- Space Complexity: `O(capacity)`
- Thread safety (for concurrent access)
- Consistent ordering and eviction

---

## Design Approach

To achieve O(1) performance:

### Data Structures Used

| Component | Purpose |
|---------|---------|
| `HashMap<K, Node>` | Fast key lookup |
| `Doubly Linked List` | Maintain usage order |

### Why Doubly Linked List?

- O(1) removal of any node
- O(1) insertion at head
- O(1) eviction from tail
- Supports move-to-head efficiently

---

## Cache Order Representation

Head (MRU) <-> Node <-> Node <-> Node <-> Tail (LRU)