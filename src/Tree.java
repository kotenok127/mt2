import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hp123 on 05.10.2018.
 */
public class Tree {
    String node;
    List<Tree> children = new ArrayList<>();

    public Tree(String node, Tree... children) {
        this.node = node;
        this.children = Arrays.asList(children);
    }

    public Tree(String node) {
        this.node = node;
    }
}