package Tree;



import java.util.ArrayList;
 
public class XMLTree<T>{
 
        private Node<T> currentElement;
 
        public XMLTree(){
                super();
        }
 
        public Node<T> getCurrentElement(){
                return currentElement;
        }
 
        public void setRootElement(Node<T> _node){
                this.currentElement = _node;
        }
 
 
        ////////////////////////////////////////////////////////////////////
        //Private Node Class
        //Each Node can act as a parent
        ////////////////////////////////////////////////////////////////////
 
        public static class Node<T>{
                public T data;
                public ArrayList<Node<T>> children;
                private Node<T> parent;
                private int currentChildIndex=0;
 
                public Node(){
                        super();
                        children = new ArrayList<Node<T>>();
                        currentChildIndex=0;
                }
                public Node(T data) {
                        this();
                        setData(data);
                        children = new ArrayList<Node<T>>();
                        currentChildIndex=0;
                }
 
                public void setParent(Node<T> _p){
                        this.parent = _p;
                }
 
                public Node<T> getParent()
                {
                        return this.parent;
                }
 
                public ArrayList<Node<T>> getChildren() {
                        if (this.children == null) {
                                return new ArrayList<Node<T>>();
                        }
                        return this.children;
                }
 
                public void setChildren(ArrayList<Node<T>> children) {
                        this.children = children;
                }
 
                public Node<T> getChildAt(int _index){
                        return children.get(_index);
                }
 
                public Node<T> getChild(Node<T> _child){
                        Node<T> c = _child;
                        for(int i=0;i<children.size();i++){
                                if(children.get(i).equals(c) ){
                                        return children.get(i);
                                }
                        }
                        return null;
                }
 
                public int getNumberOfChildren() {
                        if (children == null) {
                                return 0;
                        }
                        return children.size();
                }
 
                public void addChild(Node<T> child) {
                        children.add(child);
                }
 
                public void insertChildAt(int index, Node<T> child) throws IndexOutOfBoundsException {
                        if (index == getNumberOfChildren()) {
                                // this is really an append
                                addChild(child);
                                return;
                        } else {
                                children.get(index); //just to throw the exception, and stop here
                                children.add(index, child);
                        }
                }
                public void removeChildAt(int index) throws IndexOutOfBoundsException {
                        children.remove(index);
                }
 
 
                public int getCurrentChildIndex(){ return currentChildIndex;}
 
                public void setCurrentChildIndex(int _index){currentChildIndex=_index;}
 
                public T getData() {
                        return this.data;
                }
 
                public void setData(T data) {
                        this.data = data;
                }
 
                public Node<T> getNode(){ return this; }
 
                public String toString() {
                        StringBuilder sb = new StringBuilder();
                        sb.append("{").append(getData().toString()).append(",[");
                        int i = 0;
                        for (Node<T> e : getChildren()) {
                                if (i > 0) {
                                        sb.append(",");
                                }
                                sb.append(e.getData().toString());
                                i++;
                        }
                        sb.append("]").append("}");
                        return sb.toString();
                }
        }
 
}