package vinamrasareen.comaniacs.com.weddingplan.provider;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

@Database(version = AnnouncementDatabase.VERSION)
public class AnnouncementDatabase {

    public static final int VERSION = 4;

    @Table(AnnouncementContract.class)
    public static final String ANNOUNCEMENT_MESSAGES = "announcement_messages";
}
