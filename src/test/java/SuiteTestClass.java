import com.phonebook.web.ContactControllerTest;
import com.phonebook.web.UserControllerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author Zabudskyi Oleksandr zabudskyioleksandr@gmail.com
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({UserControllerTest.class, ContactControllerTest.class})
public class SuiteTestClass {
}
