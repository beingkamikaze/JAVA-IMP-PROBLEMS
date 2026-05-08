public abstract class ATMHandler {

    ATMHandler nextATMHandler;
    int Denomination;

    ATMHandler(ATMHandler nextATMHandler,int Denomination)
    {
        this.nextATMHandler=nextATMHandler;
        this.Denomination = Denomination;
    }
    //template method - thinkinh
    public void processAmount(int amount,CashInventory inventory){
        int availbleNote = inventory.getAvailable(Denomination);
        int requiredNotes = amount/Denomination;
        int notesToDispense = Math.min(availbleNote,requiredNotes);
        int remainder = amount-(notesToDispense*Denomination);

        if(notesToDispense>0)
        {
            inventory.deduct(Denomination,notesToDispense);
            System.out.println(Denomination + " X "+notesToDispense);
        }

        if (remainder >0 )
        {
            if(nextATMHandler!=null)
            nextATMHandler.processAmount(remainder,inventory);
            else {
                throw new IllegalArgumentException("cannot dispense remaining amount : "+ remainder);
            }
        }
    }
}
