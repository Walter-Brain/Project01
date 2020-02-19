package task;

import java.io.File;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA
 * Description:
 * User:S-
 * Date:2020/1/5-16:09
 * Version: 1.0
 **/

public interface FileScanCallback {
    void execute(File dir);
}
