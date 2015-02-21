package hvz.server;
/**
 * Web Resource for a player object
 * @author paul
 *
 */
public class UserResource {

    private final long id;
    private final String content;

    public UserResource(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
