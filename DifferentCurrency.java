
import java.util.Currency;
import java.util.Locale;
import java.util.*;
public class DifferentCurrency
{
	//currency
    private static Currency c2 = Currency.getInstance("JPY");
	private static Currency c3 = Currency.getInstance("USD");
	private static Currency c1 = Currency.getInstance(Locale.FRANCE);
	//symbol
	public static String sym1 = c1.getSymbol();
	public static String sym2 = c2.getSymbol();
	public static String sym3 = c3.getSymbol();
	//Display name
    public static String cur2 = c2.getDisplayName();
    public static String cur1 = c1.getDisplayName();
    public static String cur3 = c3 .getDisplayName();
    
    

	public static void main(String[] args)
    {
    	
        Currency c1 = Currency.getInstance(Locale.FRANCE);
        Currency c2 = Currency.getInstance("JPY"); 
        Currency c3 = Currency.getInstance("USD");  
       
  
        System.out.println("France Symbol : "+c1.getSymbol());
        System.out.println("JPY Symbol : "+c2.getSymbol());
        System.out.println("USD Symbol : "+c3.getSymbol());
        
  
    }
	
}