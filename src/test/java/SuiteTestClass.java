import com.phonebook.web.ContactControllerTest;
import com.phonebook.web.UserControllerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by Zabudskyi Oleksandr zabudskyioleksandr@gmail.com.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({UserControllerTest.class, ContactControllerTest.class})
public class SuiteTestClass {
}
