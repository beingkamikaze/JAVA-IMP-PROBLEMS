public class ATM {
    private final ATMHandler chain;
    private final CashInventory inventory;

    public ATM() {
        ATMHandler oneHundred = new OneHundredHandler(null);
        ATMHandler fiveHundred = new FiveHundredHandler(oneHundred);

        //entry point
        this.chain = new TwoThousandHandler(fiveHundred);
        this.inventory = new CashInventory();

        //initialInventory
        inventory.add(HandlerLevel.TWO_THOUSAND.value(), 2);
        inventory.add(HandlerLevel.FIVE_HUNDRED.value(), 4);
        inventory.add(HandlerLevel.ONE_HUNDRED.value(), 10);
    }

    public void withdraw(int amount)
    {
        if(amount<=0 || amount %100 !=0)
        {
            throw new IllegalArgumentException("Invalid amount");
        }

        chain.processAmount(amount,inventory);
    }


}
