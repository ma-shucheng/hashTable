package com.shuke.hash.tree;

/**
 *
 * <p>
 * 基本插入红黑树
 * </p>
 *
 * @author SCA-Liu
 * @version v1.0.0
 * @since 2020-06-05 09:46:36
 * @see com.shuke.hash.tree.TreeNode
 *
 */
public class TreeNode implements Node{

    /**
     * 定义红色为0，黑色为1
     */
    private final int R = 0;
    private final int B = 1;

    /**
     * 插入的新节点
     */
    private TreeNode newNode;
    /**
     * 目前的根节点
     */
    private TreeNode curRoot = null;

    /**
     * 节点数
     */
    private int num = 0;

    /**
     * 红黑树节点定义
     */
    int hash;
    int key;
    int val;
    int color = R;
    TreeNode left;
    TreeNode right;
    TreeNode parent;


    public TreeNode() {

    }
    

    public TreeNode(int key, int val, TreeNode parent) {
        this.key = key;
        this.val = val;
        this.parent = parent;
    }


    /**
     * 根节点不为空默认进入函数，插入新节点
     *
     * @param root
     * @param val
     */
    public void insert(int key, int val, TreeNode root) {
        //表示插入到右边
        if (root.key < key) {
            if (root.right == null) {
                root.right = new TreeNode(key, val, root);
                newNode = root.right;
                num++;
            }
            //未到叶子节点继续插入
            else {
                insert(key, val, root.right);
            }
        }
        //表示插入到左边
        else {
            if (root.left == null) {
                root.left = new TreeNode(key, val, root);
                newNode = root.left;
                num++;
            }
            //未到叶子节点
            else {
                insert(key, val, root.left);
            }
        }
    }


    /**
     * 左旋
     * @param pivot
     */
    private void leftRotate(TreeNode pivot) {
        TreeNode father = pivot.parent;
        TreeNode grandfather = father.parent;
        //如果有左子树，则将左子树作为father的右子树，如果无赋值为空
        father.right = pivot.left;
        if (pivot.left != null) {
            pivot.left.parent = father;
        }
        //父亲和pivot调换位置，更新关系
        pivot.left = father;
        father.parent = pivot;
        //不为空则进行还原工作
        if (grandfather != null) {
            //确定是爷爷的左节点还是右节点需要更改，true是左，false是右
            boolean leftFlag = father == grandfather.left;
            //接上之前的位置，更新亲子关系
            if (leftFlag) {
                grandfather.left = pivot;
            } else {
                grandfather.right = pivot;
            }
        }
        //更新关系
        pivot.parent = grandfather;
    }

    /**
     * 右旋
     * @param pivot
     */
    private void rightRotate(TreeNode pivot) {
        TreeNode father = pivot.parent;
        TreeNode grandfather = father.parent;
        //如果有左子树，则将左子树作为father的右子树，如果无赋值为空
        father.left = pivot.right;
        if (pivot.right != null) {
            pivot.right.parent = father;
        }
        //父亲和pivot调换位置
        pivot.right = father;
        father.parent = pivot;
        if (grandfather != null) {
            //确定是爷爷的左节点还是右节点需要更改，true是左，false是右
            boolean leftFlag = father == grandfather.left;
            //接上之前的位置
            if (leftFlag) {
                grandfather.left = pivot;
            } else {
                grandfather.right = pivot;
            }
        }
        //更新关系
        pivot.parent = grandfather;
    }

    /**
     * 创建红黑树，每次都需要输入更新后的根节点
     * @param key
     * @param val
     * @param root
     */
    private void createRBTree(int key, int val, TreeNode root) {
        //情景1：空树直接插入，根节点设置为黑色
        if (root == null) {
            root = new TreeNode(key, val, null);
            root.color = B;
            //记录根节点
            curRoot = root;
            num++;
            return;
        }
        //先插入节点
        insert(key, val, root);
        TreeNode newInNode = newNode;
        //情景3：插入节点的父节点为黑节点，直接插入，不做任何处理，因此没有条件直接跳过
        //情景4：插入节点的父节点为红色，需要平衡操作
        if (newInNode.parent.color == R) {
            balanceTree(newInNode);
        }
        //记录更新后的根节点
        curRoot = findRoot(newInNode);
    }

    /**
     * 寻找根节点
     * @param node
     * @return
     */
    private TreeNode findRoot(TreeNode node) {
        while (node.parent != null) {
            node = node.parent;
        }
        return node;
    }

    /**
     * 对刚插入的节点为轴进行平衡操作
     * @param newInNode
     */
    private void balanceTree(TreeNode newInNode) {
        //特殊情况处理
        //特殊1：如果为根节点，直接变黑色并退出
        if (newInNode.parent == null) {
            newInNode.color = B;
            return;
        }
        //特殊2：父节点是根节点，直接退出
        if (newInNode.parent.parent == null) {
            return;
        }
        TreeNode father = newInNode.parent;
        TreeNode grandfather = father.parent;
        //不是父亲的爷爷的子树为叔叔
        TreeNode uncle = grandfather.left != father ? grandfather.left : grandfather.right;
        //叔叔节点不存在或者为黑色节点
        boolean invalidUncle = uncle == null || uncle.color == B;
        //父亲是爷爷的左子节点
        boolean fatherIsLeft = grandfather.left == father;
        //新节点是父节点的左子节点
        boolean newIsLeft = father.left == newInNode;
        //情景1：叔叔节点存在并且为红色
        if (uncle != null && uncle.color == R) {
            father.color = B;
            uncle.color = B;
            grandfather.color = R;
            //将爷爷作为新插入的点，做平衡判断
            balanceTree(grandfather);
        }
        //情景2：叔叔节点不存在或者为黑节点，插入节点的父亲节点是祖父节点的左子节点
        else if (invalidUncle && fatherIsLeft) {
            //插入节点为左节点
            if (newIsLeft) {
                father.color = B;
                grandfather.color = R;
                rightRotate(father);
            } else {
                //左旋传化为上一问题
                leftRotate(newInNode);
                newInNode.color = B;
                grandfather.color = R;
                rightRotate(newInNode);
            }
        }
        //情景3：叔叔节点不存在或黑节点，并且插入节点的父亲是爷爷的右子节点
        else if (invalidUncle && !fatherIsLeft) {
            //插入节点为右节点
            if (!newIsLeft) {
                father.color = B;
                grandfather.color = R;
                leftRotate(father);
            } else {
                rightRotate(newInNode);
                newInNode.color = B;
                grandfather.color = R;
                leftRotate(newInNode);
            }
        }
    }


    @Override
    public void insertNode(int hash, int key, int val) {
        this.hash = hash;
        createRBTree(key, val, curRoot);
    }

    @Override
    public int getHash() {
        return this.hash;
    }

    @Override
    public boolean isTree() {
        return true;
    }

    @Override
    public void printNodes() {
        if (curRoot == null) {
            System.out.println("Empty");
            return;
        }
        TreePrint.show(curRoot);
    }

    @Override
    public boolean containNode(int key) {
        return recur(curRoot, key);
    }

    @Override
    public int size() {
        return num;
    }

    /**
     * 二叉搜索树的寻找对应节点
     * @param root
     * @param key
     * @return
     */
    private boolean recur(TreeNode root, int key) {
        if (root == null) {
            return false;
        }
        //小于右子树寻找
        if (root.key < key) {
            return recur(root.right, key);
        }
        //等于返回true
        else if (root.key == key) {
            return true;
        }
        //大于左子树寻找
        else {
            return recur(root.left, key);
        }
    }
}
