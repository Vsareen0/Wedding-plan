package vinamrasareen.comaniacs.com.weddingplan.provider;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.ConflictResolutionType;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

public class AnnouncementContract {

    @DataType(DataType.Type.INTEGER)
    @PrimaryKey(onConflict = ConflictResolutionType.REPLACE)
    @AutoIncrement
    public static final String COLUMN_ID = "id";

    @DataType(DataType.Type.TEXT)
    @NotNull
    public static final String COLUMN_MESSAGE = "message";

    @DataType(DataType.Type.INTEGER)
    public static final String COLUMN_DATE = "date";
}
