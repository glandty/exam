import java.util.LinkedList;
import java.util.Queue;

/*二叉树的基本知识
 */
class Node {
    public char val;
    public Node left;
    public Node right;

    public Node(char val) {
        this.val = val;
    }
}
public class TestTree {
    public static Node build() {
        Node a = new Node('A');
        Node b = new Node('B');
        Node c = new Node('C');
        Node d = new Node('D');
        Node e = new Node('E');
        Node f = new Node('F');
        Node g = new Node('G');
        Node h = new Node('H');

        a.left = b;
        a.right = c;
        b.left = d;
        b.right = e;
        e.left = g;
        g.right = h;
        c.right = f;

        return a;
    }

    //先序遍历
    public static void preOrder(Node root) {
        if(root == null) {
            return;
        }
        System.out.print(root.val + " ");
        preOrder(root.left);
        preOrder(root.right);
    }

    //中序遍历
    public static void inOrder(Node root) {
        if(root == null) {
            return;
        }
        inOrder(root.left);
        System.out.print(root.val + " ");
        inOrder(root.right);
    }

    //后序遍历
    public static void postOrder(Node root) {
        if(root == null) {
            return;
        }
        postOrder(root.left);
        postOrder(root.right);
        System.out.print(root.val + " ");
    }

    //层序遍历
    public static  void levelOrder(Node root) {
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            System.out.print(cur.val + " ");
            if(cur.left != null) {
                queue.offer(cur.left);
            }
            if(cur.right != null) {
                queue.offer(cur.right);
            }
        }
    }

    public static int size(Node root) {
        if(root == null) {
            return 0;
        }
        return 1 + size(root.left) + size(root.right);
    }

    public static int leafsize(Node root) {
        if(root== null) {
            return 0;
        }
        if(root.right == null && root.left == null) {
            return 1;
        }
        return leafsize(root.right) + leafsize(root.left);
    }

    public static int ksize(Node root, int k) {
        if(k < 1 || root == null) {
            return 0;
        }
        if(k == 1) {
            return 1;
        }
        return ksize(root.left,k - 1) + ksize(root.right, k - 1);
    }

    Node find(Node root,char toFind) {
        if(root == null) {
            return null;
        }
        if(root.val == toFind) {
            return root;
        }
        Node result = find(root.left, toFind);
        if(result != null) {
            return result;
        }
        return find(root.right, toFind);
    }

    //比较两个树是否相等
    public boolean isSameTree(Node p, Node q) {
        if(p == null && q == null) {
            return true;
        }
        if((p == null && q != null) || (p != null && q == null)) {
            return false;
        }
        if(p.val != q.val) {
            return false;
        }
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    //判断t是否为s的一个子树
    public boolean isSubTree(Node t, Node s) {
        if(s == null && t == null) {
            return true;
        }
        if(s == null || t == null) {
            return false;
        }
        boolean ret = false;
        if(s.val == t.val) {
            ret = isSubTree(t, s);
        }
        if(!ret) {
            ret = isSubTree(s.left,t);
        }
        if(!ret) {
            ret = isSubTree(s.right,t);
        }
        return ret;
    }

    //求树的最大深度
    public int maxDepth(Node root) {
        if(root == null) {
            return 0;
        }
        if(root.left == null && root.right == null) {
            return 1;
        }
        return 1 + (maxDepth(root.right) > maxDepth(root.left) ? maxDepth(root.right) : maxDepth(root.left));
    }

    //树是否是平衡的
    public boolean isBalanced(Node root) {
        if(root == null) {
            return true;
        }
        if(root.left == null && root.right == null) {
            return true;
        }
        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);
        if((leftDepth - rightDepth) < -1 || (leftDepth - rightDepth) > 1) {
            return false;
        }
        return isBalanced(root.right) && isBalanced(root.left);
    }

    //树是不是镜像对称
    public boolean isSymmetric(Node root) {
        if(root == null) {
            return true;
        }
        return isMirror(root.right, root.left);
    }

    private boolean isMirror(Node t1, Node t2) {
        if(t1 == null && t2 == null) {
            return true;
        }
        if(t1 == null || t2 == null) {
            return false;
        }
        if(t1.val != t2.val) {
            return false;
        }
        return isMirror(t1.left, t2.right) && isMirror(t1.right, t2.left);
    }

    //是否是完全二叉树
    public boolean isCompleteTree(Node root) {
        if(root == null) {
            return true;
        }
        boolean isSecondStep = false;

        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node cur = queue.poll();

            if (!isSecondStep) {
                if (cur.right != null && cur.left != null) {
                    queue.offer(cur.right);
                    queue.offer(cur.left);
                } else if (cur.left == null && cur.right != null) {
                    return false;
                } else if (cur.left != null && cur.right == null) {
                    isSecondStep = true;
                    queue.offer(cur.left);
                } else {
                    isSecondStep = true;
                }

                if (cur.left != null) {
                    queue.offer(cur.left);
                }
                if (cur.right != null) {
                    queue.offer(cur.right);
                }
            } else {
                if(cur.right != null || cur.left != null) {
                    return false;
                }
            }
        }
        return true;
    }

    //lca 表示最近公共祖先
    private Node lca = null;
    //求最近公共祖先
    public Node lowestCommonAncestor(Node root, Node p, Node q) {
        if(root == null) {
            return  null;
        }
        findNode(root, p, q);
        return lca;
    }
    private boolean findNode(Node root, Node p, Node q) {
        if(root == null) {
            return false;
        }
        int left = findNode(root.left, p , q) ? 1 : 0;
        int right = findNode(root.right, p, q) ? 1 : 0;
        int mid = (root == p || root == q) ? 1 : 0;
        if(left + right + mid == 2) {
            lca = root;
        }
        return (left + right + mid) > 0;
    }

    //二叉搜索树构建排序的双向链表
    public Node Convert(Node pRootOfTree) {
        if(pRootOfTree == null) {
            return null;
        }
        if(pRootOfTree.left == null && pRootOfTree.right == null) {
            return pRootOfTree;
        }
        Node left = Convert(pRootOfTree.left);
        Node leftTail = left;
        while(leftTail != null && leftTail.right != null) {
            leftTail = leftTail.right;
        }
        if(left != null) {
            leftTail.right = pRootOfTree;
            pRootOfTree.left = leftTail;
        }
        Node right = Convert(pRootOfTree.right);
        if(right != null) {
            right.left = pRootOfTree;
            pRootOfTree.right = right;
        }
        return left == null ? pRootOfTree : left;
    }

    //给定先序\中序求树
    private int index;
    public Node buildTree(int[] preorder, int[] inorder) {
        index = 0;
        return  buildTreeHelp(preorder, inorder, 0,inorder.length);
    }

    private Node buildTreeHelp(int[] preorder, int[] inorder, int left, int right) {
        if(left >= right) {
            return null;
        }
        if(index >= preorder.length) {
            return null;
        }
        Node root =new Node(preorder[index]);
        index++;
        int pos = find(inorder, left, right, root.val);
        root.left = buildTreeHelp(preorder, inorder, left, pos);
        root.right = buildTreeHelp(preorder, inorder, pos+1, right);
        return root;
    }

    private int find(int[] inorder, int left, int right, int toFind) {
        for (int i = left; i < right; i++) {
            if(inorder[i] == toFind) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        Node root = build();
        System.out.println("节点个数为:" + size(root));
        System.out.println("先序遍历:");
        preOrder(root);
        System.out.println();

        System.out.println("中序遍历:");
        inOrder(root);
        System.out.println();

        System.out.println("后续遍历:");
        postOrder(root);
        System.out.println();

        System.out.println("层序遍历:");
        levelOrder(root);
    }
}