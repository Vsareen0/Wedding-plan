package vinamrasareen.comaniacs.com.weddingplan.provider;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

@ContentProvider(
        authority = AnnouncementProvider.AUTHORITY,
        database = AnnouncementDatabase.class)
public final class AnnouncementProvider {

    public static final String AUTHORITY = "vinamrasareen.comaniacs.com.weddingplan.provider.provider";

    @TableEndpoint(table = AnnouncementDatabase.ANNOUNCEMENT_MESSAGES)
    public static class AnnouncementMessages{

        @ContentUri(
                path = "messages",
                type = "vnd.android.cursor.dir/messages",
                defaultSort = AnnouncementContract.COLUMN_DATE + "DESC")
            public static final Uri CONTENT_URI = Uri.parse("content://"+ AUTHORITY + "/messages");
    }
}
