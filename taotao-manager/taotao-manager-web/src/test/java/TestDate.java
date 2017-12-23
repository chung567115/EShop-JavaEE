import org.joda.time.DateTime;
import org.junit.Test;

/**
 * @author Charle Chung created on 2017年11月28日
 */
public class TestDate {
	@Test
	public void testDate() {
		System.out.println((new DateTime().toString("/yyyy/MM/dd")));
	}
}
