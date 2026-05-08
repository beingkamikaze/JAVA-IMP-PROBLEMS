/**
 * THREAD-SAFETY GUIDE FOR TIC-TAC-TOE GAME
 * 
 * ==============================================================
 * PROBLEMS IN NON-THREAD-SAFE VERSION:
 * ==============================================================
 * 
 * 1. RACE CONDITION IN Game.play():
 *    - Multiple threads can check gameStatus at the same time
 *    - Multiple threads can increment 'moves' simultaneously
 *    - Multiple threads can change 'currentPlayerIndex' concurrently
 *    - Result: Invalid game state, duplicate moves, corrupted data
 * 
 * 2. RACE CONDITION IN Board.makemove():
 *    - Thread A reads grid[row][col] = '\0' (valid)
 *    - Thread B reads grid[row][col] = '\0' (valid) 
 *    - Both threads write their symbol at same location
 *    - Result: One move accidentally overwritten
 * 
 * 3. MEMORY VISIBILITY ISSUES:
 *    - Changes to gameStatus might not be visible to other threads
 *    - Thread caches might have stale values
 *    - Result: Threads see old state even after updates
 * 
 * ==============================================================
 * SOLUTION 1: SYNCHRONIZED METHODS (Simple)
 * ==============================================================
 * 
 * Approach:
 * - Mark critical methods with 'synchronized' keyword
 * - Only one thread can execute synchronized method at a time
 * - Others wait in a queue
 * 
 * Pros:
 *   ✓ Simple to implement
 *   ✓ Built-in Java feature (uses intrinsic lock)
 *   ✓ Automatic lock release (even on exceptions)
 *   ✓ Less code to write
 * 
 * Cons:
 *   ✗ Coarser locking (entire method blocked)
 *   ✗ Less flexible (can't acquire/release manually)
 *   ✗ Can't set timeout on lock acquisition
 *   ✗ Can't use conditions (wait/notify is complex)
 * 
 * When to use:
 *   - Simple use cases
 *   - Methods are small/quick
 *   - Don't need advanced features
 * 
 * Code Example:
 * 
 *   public synchronized void makemove(int row, int col, Player player) {
 *       grid[row][col] = player.Symbol;
 *   }
 * 
 * ==============================================================
 * SOLUTION 2: REENTRANT LOCK (Flexible)
 * ==============================================================
 * 
 * Approach:
 * - Use java.util.concurrent.locks.ReentrantLock
 * - Manually acquire and release locks
 * - Always use try-finally to ensure unlock
 * 
 * Pros:
 *   ✓ Fine-grained control
 *   ✓ Can try to acquire with timeout: lock.tryLock(timeout)
 *   ✓ Can use Conditions for wait/notify
 *   ✓ More readable (explicit lock/unlock)
 *   ✓ Multiple condition variables possible
 * 
 * Cons:
 *   ✗ More verbose code
 *   ✗ Must remember finally block (forgetting = deadlock)
 *   ✗ Requires careful exception handling
 * 
 * When to use:
 *   - Complex locking requirements
 *   - Need tryLock timeout feature
 *   - Need Conditions for synchronization
 *   - Performance critical (lock can be reentrant for same thread)
 * 
 * Code Example:
 * 
 *   private final ReentrantLock lock = new ReentrantLock();
 *   
 *   public void makemove(int row, int col, Player player) {
 *       lock.lock();
 *       try {
 *           grid[row][col] = player.Symbol;
 *       } finally {
 *           lock.unlock();
 *       }
 *   }
 * 
 * ==============================================================
 * SOLUTION 3: VOLATILE KEYWORD (Light-weight)
 * ==============================================================
 * 
 * Approach:
 * - Use 'volatile' for simple shared variables
 * - Ensures memory visibility but NOT atomicity
 * - No blocking - threads read latest value
 * 
 * Pros:
 *   ✓ No blocking/locking
 *   ✓ Better performance for simple reads/writes
 *   ✓ Guarantees memory visibility
 * 
 * Cons:
 *   ✗ Only for simple variables (can't use on arrays/objects)
 *   ✗ No atomicity (compound operations fail)
 *   ✗ Limited use cases
 * 
 * When to use:
 *   - Simple flag variables (boolean, int, long)
 *   - Only reads and single writes
 *   - Performance needs to be high
 * 
 * Code Example:
 * 
 *   private volatile GameStatus gameStatus = GameStatus.ONGOING;
 *   
 *   // Safe reads from multiple threads
 *   if (gameStatus == GameStatus.ONGOING) { ... }
 * 
 * ==============================================================
 * SOLUTION 4: COPY-ON-WRITE (Immutable approach)
 * ==============================================================
 * 
 * Approach:
 * - Create new copies instead of modifying shared data
 * - Use CopyOnWriteArrayList for thread-safe lists
 * - Readers don't write, so no synchronization needed for reads
 * 
 * Pros:
 *   ✓ Reads are extremely fast (no locking)
 *   ✓ Thread-safe for concurrent reads
 *   ✓ Safe for multi-threaded iteration
 * 
 * Cons:
 *   ✗ Writes are expensive (copy entire array)
 *   ✗ Memory overhead (multiple copies)
 *   ✗ Only suitable for read-heavy workloads
 * 
 * When to use:
 *   - Listeners/observers pattern
 *   - Read-heavy operations
 *   - Small collections
 * 
 * ==============================================================
 * SOLUTION 5: ATOMIC CLASSES (Non-blocking)
 * ==============================================================
 * 
 * Approach:
 * - Use java.util.concurrent.atomic.AtomicInteger, AtomicLong, etc.
 * - Uses Compare-And-Swap (CAS) instead of locking
 * - Spin-retry on failure without blocking
 * 
 * Pros:
 *   ✓ Faster than locks in low-contention scenarios
 *   ✓ No thread blocking
 *   ✓ Simple API
 *   ✓ Good for counters/flags
 * 
 * Cons:
 *   ✗ Limited functionality (only simple types)
 *   ✗ Busy-waiting under high contention
 *   ✗ Can't protect complex operations
 * 
 * When to use:
 *   - Simple counters/flags
 *   - Low-contention scenarios
 *   - Need performance critical operations
 * 
 * Code Example:
 * 
 *   private AtomicInteger moves = new AtomicInteger(0);
 *   private AtomicReference<GameStatus> gameStatus = 
 *       new AtomicReference<>(GameStatus.ONGOING);
 * 
 * ==============================================================
 * COMPARISON TABLE:
 * ==============================================================
 * 
 * Feature              | Synchronized | ReentrantLock | Volatile | Atomic
 * -------------------|--------------|---------------|----------|--------
 * Simple to use       | Yes          | No            | Yes      | Yes
 * Performance         | Good         | Better        | Best     | Very Good
 * Timeout support     | No           | Yes           | No       | No
 * Condition vars      | Yes (notify) | Yes           | No       | No
 * Complex ops         | Yes          | Yes           | No       | No
 * Deadlock risk       | Yes          | Yes           | No       | No
 * ----------|-----------|---------|---------|-------
 * 
 * ==============================================================
 * BEST PRACTICE FOR TIC-TAC-TOE:
 * ==============================================================
 * 
 * Recommendation: Use ReentrantLock for Game + Board classes
 * 
 * Reasons:
 * 1. Game.play() requires atomic operation (check, move, update)
 *    - Single lock ensures no interleaving
 * 2. Board operations are independent
 *    - Can individually lock each operation
 * 3. Future extensions may need tryLock() or Conditions
 * 4. Better performance under contention
 * 
 * ==============================================================
 * COMMON MISTAKES TO AVOID:
 * ==============================================================
 * 
 * ❌ WRONG - Forgetting finally block:
 *    lock.lock();
 *    grid[row][col] = player.Symbol;  // Exception here = DEADLOCK
 *    lock.unlock();
 * 
 * ✓ RIGHT - Always use try-finally:
 *    lock.lock();
 *    try {
 *        grid[row][col] = player.Symbol;
 *    } finally {
 *        lock.unlock();
 *    }
 * 
 * ❌ WRONG - Holding lock during I/O:
 *    lock.lock();
 *    try {
 *        scanner.nextLine();  // Blocks entire game!
 *    } finally { lock.unlock(); }
 * 
 * ✓ RIGHT - Minimize critical section:
 *    String input = scanner.nextLine();  // Do I/O outside lock
 *    lock.lock();
 *    try {
 *        game.play(row, col);
 *    } finally { lock.unlock(); }
 * 
 * ❌ WRONG - Different locks for related operations:
 *    lock1.lock(); board operations
 *    lock2.lock(); game state
 *    // Possible deadlock!
 * 
 * ✓ RIGHT - Use single lock for atomic operations:
 *    gameLock.lock();
 *    try {
 *        if (board.isValidMove(row, col)) {
 *            board.makemove(row, col, player);
 *            moves++;
 *        }
 *    } finally { gameLock.unlock(); }
 * 
 * ==============================================================
 * TESTING THREAD SAFETY:
 * ==============================================================
 * 
 * 1. Use JUnit
 * 2. Create multiple threads making moves
 * 3. Verify final board state is consistent
 * 4. Check no moves were lost or duplicated
 * 5. Verify game ended in valid state
 * 
 * ==============================================================
 */

