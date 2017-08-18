import org.junit.Test;

/**
 * Created by lizq on 2017/8/18.
 */
public class OSTest {

    @Test
    public void whichOS(){
       String os= System.getProperty("os.name");
       System.out.println(os);
    }
}
