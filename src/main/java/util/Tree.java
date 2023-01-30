package util;

import java.util.ArrayList;
import java.util.List;

public class Tree<R> {
    public R data;
    public List<Tree<R>> subTrees = new ArrayList<>();

}
