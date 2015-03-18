package Tree;
import java.util.ArrayList;


import Tree.XMLTree.Node;
 
public class XMLTreeHandler {
 
        XMLTree<Object> tree;
        private Node<Object> current;
        private Node<Object> root;
       
        public XMLTreeHandler( Node<Object> _root){
                tree = new XMLTree<Object>();     
                root = _root;
                tree.setRootElement(root);
                current = tree.getCurrentElement();
        }
 
        public void addChild(Node<Object> _c){
            Node<Object> c = _c;
                c.setParent(current.getNode());
 
            tree.getCurrentElement().setParent(current);
                tree.getCurrentElement().addChild(c);
        }
       
        public boolean moveToParent(){
                if(current.getParent() != null){
                        tree.setRootElement(tree.getCurrentElement().getParent());
                    current = tree.getCurrentElement();
                    int index = tree.getCurrentElement().getNumberOfChildren();
                    tree.getCurrentElement().setCurrentChildIndex(index+1);
                    return true;
                }
                System.out.println("Already at root node!");
                return false;
        }
        public boolean moveToRoot(){
                if(current == root){
                        System.out.println("Already at root");
                        return false;
                }
                tree.setRootElement(root);
                return true;        
        }
       
        public boolean isCurrentAtRoot(){
                if(current.equals(root))
                        return true;
                return false;
        }
       
        public void addData(Object _o){
                tree.getCurrentElement().setData(_o);
                current.setData(_o);
        }
       
        public boolean hasChild(Node<Object> _child){
                for(int i=0;i<tree.getCurrentElement().getNumberOfChildren();i++)
                {      
                        if(tree.getCurrentElement().getChildAt(i).getData().equals(_child.getData()))
                                return true;
                }
                return false;
        }
       
        public boolean moveToChildAt(int _index){
                if(tree.getCurrentElement().getNumberOfChildren() > _index && _index >= 0){
                        current = tree.getCurrentElement().getChildAt(_index);
                        tree.setRootElement(current);
                        return true;
                }
                return false;
        }
        public boolean moveToNextChild(){
                int index = tree.getCurrentElement().getCurrentChildIndex();
                if(index < tree.getCurrentElement().getNumberOfChildren()){
                        current = tree.getCurrentElement().getChildAt(index);
                        tree.setRootElement(current);
                       
                       // XMLRaptorObject r =(XMLRaptorObject) tree.getCurrentElement().getData();
                        tree.getCurrentElement().setCurrentChildIndex(index+1);
                        return true;
                }
                return false;
        }
        public boolean moveToPrevChild(){
                int index = tree.getCurrentElement().getCurrentChildIndex();
                if(index >0){
                        current = tree.getCurrentElement().getChildAt(index-1);
                        tree.setRootElement(current);
                        tree.getCurrentElement().setCurrentChildIndex(index+1);
                        return true;
                }
                return false;
        }
        public Object getChildAt(int _index){
                if(tree.getCurrentElement().getNumberOfChildren() > _index && _index >=0)
                        return tree.getCurrentElement().getChildAt(_index);
                return false;
        }
       
        //TODO:should be a try catch instead of returning null
        public Node<Object> getNextChild(){
                int index = tree.getCurrentElement().getCurrentChildIndex();
                if(index < tree.getCurrentElement().getNumberOfChildren()){
                        return tree.getCurrentElement().getChildAt(index+1);
                }
                return null;
        }
        public Node<Object> getPreviousChild(){
                int index = tree.getCurrentElement().getCurrentChildIndex();
                if(index >0){
                        return tree.getCurrentElement().getChildAt(index-1);
                }
                return null;
        }
        public void setChildIndex(int _index)                    { tree.getCurrentElement().setCurrentChildIndex(_index);}
        public int  getChildIndex(int _index)                    { return tree.getCurrentElement().getCurrentChildIndex();}
        public void setAllChildren(ArrayList<Node<Object>> nodes){ tree.getCurrentElement().children = nodes;}
        public void setParent(Node<Object> _parent)              { tree.getCurrentElement().setParent(_parent);}
        public Node<Object> getCurrentElement()                  { return tree.getCurrentElement();}
        public Object getCurrentElementData()                    { return tree.getCurrentElement().getData();}
        public int getCurrentChildrenSize()                      { return tree.getCurrentElement().getNumberOfChildren();}
        public ArrayList<Node<Object>> getAllCurrentChildren()   { return tree.getCurrentElement().getChildren();}
        public Node<Object> getParent()                          { return tree.getCurrentElement().getParent();}
       
}