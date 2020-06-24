package com.shuke.hash.tree;

/**
 *
 * <p>
 * 哈希表存储
 * </p>
 *
 * @author SCA-Liu
 * @version v1.0.0
 * @since 2020-06-09 22:09:01
 * @see com.shuke.hash.tree.Table
 *
 */
public class Table {


    /**
     * 定义一个可变的容量
     */
    int capacity;

    /**
     * 默认的初始容量，但必须为2的次方。
     */
    static final int DEFAULT_INITIAL_CAPACITY = 4;

    /**
     * 允许的最大容量，这是int值（4字节，32个bit）的正数范围内最大的2的次方。
     */
    static final int MAXIMUM_CAPACITY = 1 << 30;

    /**
     * 定义哈希链表
     */
    Node[] nodes;


    /**
     * 计算节点的哈希值，加入高位比较，这里key的值有待商榷，是使用对象哈希还是本身
     * @param key
     * @return
     */
    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    public Table() {
        capacity = DEFAULT_INITIAL_CAPACITY;
        nodes = new Node[capacity];
    }

    /**
     * 放置元素
     * @param val
     */
    public void put(int val) {
        //先待定为val值
        int key = val;
        int hash = hash(key);
        if (nodes[capacity - 1 & hash] == null) {
            nodes[capacity - 1 & hash] = new TreeNode();
        }
        Node node = nodes[capacity - 1 & hash];
        node.insertNode(hash, key, val);
    }

    /**
     * 包含对应值
     * @param val
     * @return
     */
    public boolean containVal(int val) {
        //先待定为val值
        int key = val;
        int hash = hash(key);
        //如果该位置为空，直接返回false
        if (nodes[capacity - 1 & hash] == null) {
            return false;
        }
        //不为空寻找，找到返回true
        else {
            Node node = nodes[capacity - 1 & hash];
            return node.containNode(key);
        }
    }


    /**
     * 打印值对应的所有的节点值
     * @param val
     * @return
     */
    public void printVals(int val) {
        //先待定为val值
        int key = val;
        int hash = hash(key);
        Node node = nodes[capacity - 1 & hash];
        node.printNodes();
    }

    /**
     * 打印值对应的所有的节点的数量
     * @param val
     * @return
     */
    public int sizeVals(int val) {
        //先待定为val值
        int key = val;
        int hash = hash(key);
        Node node = nodes[capacity - 1 & hash];
        return node.size();
    }


    public static void main(String[] args) {
        Table table = new Table();
        for (int i = 0; i < 30; i++) {
            table.put(i);
        }
        table.printVals(1);
    }

}
