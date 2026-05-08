//Abstract Base Decorator
public abstract class ToppingDecorator implements BasePizza {
    //it has a ref of Base Pizza
    BasePizza basePizza;
    public ToppingDecorator(BasePizza basePizza)
    {
        this.basePizza=basePizza;
    }
}
