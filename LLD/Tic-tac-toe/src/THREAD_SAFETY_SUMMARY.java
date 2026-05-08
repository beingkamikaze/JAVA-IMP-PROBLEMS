/**
 * THREAD-SAFETY SOLUTIONS - QUICK REFERENCE
 * 
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ PROBLEM: Race Conditions in Tic-Tac-Toe                                   ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 * 
 * Without synchronization, multiple threads can:
 * • Overwrite moves on same cell
 * • Cause invalid game state
 * • Miss winner detection
 * • See stale values
 * 
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ SOLUTION COMPARISON                                                        ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 * 
 * 1. SYNCHRONIZED METHODS
 *    ─────────────────────
 *    public synchronized void play(int row, int col) { ... }
 *    
 *    Best for: Simple cases, small critical sections
 *    Pros: Easy, automatic unlock, standard approach
 *    Cons: Coarse-grained, no timeout, no conditions
 *    
 *    Files created: BoardThreadSafe_Synchronized.java
 * 
 * 
 * 2. REENTRANT LOCK ⭐ RECOMMENDED FOR THIS PROJECT
 *    ───────────────────────────────────────────────
 *    private final ReentrantLock lock = new ReentrantLock();
 *    
 *    public void play(int row, int col) {
 *        lock.lock();
 *        try {
 *            // critical section
 *        } finally {
 *            lock.unlock();
 *        }
 *    }
 *    
 *    Best for: Complex logic, need advanced features
 *    Pros: Fine control, tryLock, conditions, flexible
 *    Cons: More code, must remember finally
 *    
 *    Files created: BoardThreadSafe_Lock.java, GameThreadSafe.java
 * 
 * 
 * 3. VOLATILE KEYWORD
 *    ────────────────
 *    private volatile GameStatus status = GameStatus.ONGOING;
 *    
 *    Best for: Simple flag variables
 *    Pros: No blocking, memory visibility guaranteed
 *    Cons: Only for simple types, no atomicity for compounds ops
 *    
 * 
 * 4. ATOMIC CLASSES
 *    ──────────────
 *    private AtomicInteger moves = new AtomicInteger(0);
 *    
 *    Best for: Lock-free counters and simple flags
 *    Pros: Fast, non-blocking, Compare-And-Swap
 *    Cons: Limited to simple types, not for complex logic
 *    
 *    Files created: GameThreadSafe_Atomic.java
 * 
 * 
 * 5. COPY-ON-WRITE
 *    ──────────────
 *    CopyOnWriteArrayList<Move> moves = new CopyOnWriteArrayList<>();
 *    
 *    Best for: Read-heavy, listeners, observers
 *    Pros: Fast reads, concurrent iteration safe
 *    Cons: Slow writes, memory overhead
 * 
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ FILES CREATED IN THIS SESSION                                             ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 * 
 * 1. BoardThreadSafe_Synchronized.java
 *    - Synchronized Board implementation
 *    - All methods marked synchronized
 *    - Simple but coarse-grained locking
 * 
 * 2. BoardThreadSafe_Lock.java
 *    - ReentrantLock Board implementation
 *    - Fine-grained lock control
 *    - Each method acquires/releases lock explicitly
 * 
 * 3. GameThreadSafe.java ⭐ RECOMMENDED
 *    - ReentrantLock Game implementation
 *    - Entire play() operation is atomic
 *    - Uses BoardThreadSafe_Lock
 *    - Added getter methods for state
 * 
 * 4. GameThreadSafe_Atomic.java
 *    - Atomic classes Game implementation
 *    - Lock-free for simple operations
 *    - Good for counters but not compound operations
 * 
 * 5. ThreadSafeGameTest.java
 *    - Test class with concurrent thread examples
 *    - Shows how multiple threads interact safely
 * 
 * 6. THREAD_SAFETY_GUIDE.java
 *    - Comprehensive documentation
 *    - Explains all approaches and trade-offs
 *    - Common mistakes and best practices
 * 
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ DECISION MATRIX                                                            ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 * 
 * If you need:             | Use:
 * ─────────────────────────|──────────────────────────
 * Simplicity               | Synchronized methods
 * Fine control             | ReentrantLock
 * High performance         | Atomic classes
 * Simple flags only        | Volatile
 * Read-heavy workload      | CopyOnWriteArrayList
 * Modern approach          | ReentrantLock + Conditions
 * 
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ HOW TO USE IN AN INTERVIEW                                                ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 * 
 * When asked "How would you make this thread-safe?", say:
 * 
 * "I would identify two race condition risks:
 * 
 *  1. Board state corruption (multiple threads writing same cell)
 *     → Solution: Lock during isValidMove + makemove + checkWinner
 *  
 *  2. Game state inconsistency (checking and updating gameStatus)
 *     → Solution: Make play() atomic with ReentrantLock
 * 
 * I would use ReentrantLock because:
 * • Provides atomic operations for entire play() method
 * • Better performance than synchronized under contention
 * • Allows future enhancements (timeouts, conditions)
 * • More readable - explicit lock/unlock
 * 
 * Implementation:
 * • Wrap critical section in lock.lock() try/finally lock.unlock()
 * • Minimize lock scope (don't hold during I/O)
 * • Add getters for querying state atomically
 * • Test with concurrent threads to verify correctness"
 * 
 * ╔════════════════════════════════════════════════════════════════════════════╗
 * ║ KEY CONCEPTS TO REMEMBER                                                   ║
 * ╚════════════════════════════════════════════════════════════════════════════╝
 * 
 * RACE CONDITION: When multiple threads access shared resource simultaneously
 *                 without synchronization → unpredictable results
 * 
 * CRITICAL SECTION: Code that accesses shared mutable state
 *                   Only one thread should execute at a time
 * 
 * MUTUAL EXCLUSION: Ensuring only one thread executes critical section
 *                   Achieved via locks (synchronized, ReentrantLock)
 * 
 * ATOMICITY: Operation appears to be single-step to other threads
 *            Either completely executed or not at all
 * 
 * MEMORY VISIBILITY: Changes in one thread become visible to other threads
 *                    Guaranteed by: volatile, synchronized, locks
 * 
 * DEADLOCK: Threads hold locks and wait for each other's locks
 *           Fatal: Program hangs forever
 *           Avoid: Always use try/finally, acquire locks in same order
 */

