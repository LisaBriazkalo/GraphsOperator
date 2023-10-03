import java.awt.*;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

public class Graph {
    private Vertex[] vertices = new Vertex[0];
    private Edge[] edges = new Edge[0];
    private int countOfVertices;
    private int countOfEdges;
    private Boolean direct;

    public Graph() {
        countOfVertices=0;
        countOfEdges=0;
        direct=false;
    }
    public void weightOn(){
        for(int i=0; i<edges.length;i++){
            int count=0;
            Vertex source=null;
            Vertex destination=null;
            for(int j=0; j<edges.length;j++){
                if(edges[i].getSource()==edges[j].getSource() &&
                        edges[i].getDestination()==edges[j].getDestination()) {
                    count++;
                    source=edges[i].getSource();
                    destination=edges[i].getDestination();
                    removeEdge(edges[i].getSource(), edges[j].getDestination());
                }
            }
            if(count!=0){
                Edge e = new Edge(source,destination,count);
                e.setShowWeight(true);
                addEdge(e);
            }
        }
    }
    Vertex findVertex(int x, int y){
        int i;
        for(i=0;i<countOfVertices;i++){
            if(Math.abs(vertices[i].getX()-x)<=20 && Math.abs(vertices[i].getY()-y)<=20)
                return vertices[i];
        }
        return null;
    }
    public void clear(){
        edges=Arrays.copyOf(edges,0);
        countOfEdges=0;
    }
    public void clearAll(){
        vertices=Arrays.copyOf(vertices,0);
        edges=Arrays.copyOf(edges,0);
        countOfVertices=0;
        countOfEdges=0;
    }
    public void addVertex(int num, int x, int y){
        Boolean b=false;
        for(int i=0; i<countOfVertices;i++){
            if(vertices[i].getNum()==num)
                b=true;
        }
        if(!b){
            countOfVertices++;
            vertices = Arrays.copyOf(vertices,countOfVertices);
            vertices[countOfVertices-1]=new Vertex(num,x,y);
        }
    }
    public Vertex findVertex(int num){
        for(int i=0;i<countOfVertices;i++){
            if(vertices[i].getNum()==num)
                return vertices[i];
        }
        return null;
    }
    public void addEdge(Vertex source, Vertex destination, int weight){
        countOfEdges++;
        edges= Arrays.copyOf(edges,countOfEdges);
        edges[countOfEdges-1]=new Edge(source,destination,weight);
    }
    public void addEdge(Edge edge){
        countOfEdges++;
        edges= Arrays.copyOf(edges,countOfEdges);
        edges[countOfEdges-1]=new Edge(edge.getSource(),edge.getDestination(),edge.getWeight());
        edges[countOfEdges-1].setShowWeight(edge.getShowWeight());
    }
    public void removeVertex(Vertex vertex){
        int i=0;
        for( ;i<countOfVertices;i++){
            if(vertices[i]==vertex)
                break;
        }
        for( ;i<countOfVertices-1;i++){
            vertices[i]=vertices[i+1];
        }
        countOfVertices--;
        vertices = Arrays.copyOf(vertices,countOfVertices);
        for(i=0; i<countOfVertices;i++){
            for(int j=0; j<countOfEdges;j++){
                if(edges[j].getSource().getNum()==vertex.getNum() ||
                        edges[j].getDestination().getNum()==vertex.getNum()) {
                    removeEdge(edges[j].getSource(),edges[j].getDestination());
                }
            }
        }
    }
    public void removeEdge(Vertex source, Vertex destination){
        int i=0;
        for(; i<countOfEdges; i++){
            if(edges[i].getSource()==source &&
                    edges[i].getDestination()==destination)
                break;
        }
        for(;i<countOfEdges-1;i++){
            edges[i]=edges[i+1];
        }
        countOfEdges--;
        edges = Arrays.copyOf(edges,countOfEdges);
    }
    public StringBuilder displayGraph(){
        StringBuilder sb = new StringBuilder("Граф:\n");
        for(int i=0; i<countOfVertices;i++){
            sb.append(vertices[i].getNum()+":");
            if(direct==false){
            for(int j=0; j<countOfEdges;j++){
                if(edges[j].getSource().getNum()==vertices[i].getNum() ||
                        edges[j].getDestination().getNum()==vertices[i].getNum()) {
                    if (edges[j].getSource().getNum() == vertices[i].getNum())
                        sb.append("\t" + edges[j].getDestination().getNum());
                    else sb.append("\t" + edges[j].getSource().getNum());
                }
            }}
            if(direct==true){
                for(int j=0; j<countOfEdges;j++){
                    if(edges[j].getSource().getNum()==vertices[i].getNum()) {
                            sb.append("\t" + edges[j].getDestination().getNum());
                    }
                }
            }
            sb.append("\n");
        }
        return sb;
    }
    private int deg(Vertex vertex){
        int count=0;
        for(int i=0;i<countOfEdges;i++){
            if(vertex.equals(edges[i].getSource()) ||
                    vertex.equals(edges[i].getDestination()))
                count++;
        }
        return count;
    }         //Штучьки
    private Edge findFirstMinWeight(){
        Edge min=edges[0];
        for(int i=0;i<countOfEdges;i++){
            if(edges[i].getWeight()<min.getWeight())
                min=edges[i];
        }
        return min;
    }
    private Edge findMinEdge(Vertex[] arr, Graph span){
        Edge[] eArr = new Edge[0];
        for(int v=0; v<arr.length;v++)
            for(int i=0; i<countOfEdges;i++){
                if(edges[i].getSource().getNum()==arr[v].getNum() ||
                        edges[i].getDestination().getNum()==arr[v].getNum()){
                    Boolean b=true;
                    for(int j=0;j<eArr.length;j++){
                        if(eArr[j].equals(edges[i]))
                            b=false;
                    }
                    for(int j=0;j<span.countOfEdges;j++){
                        if(span.edges[j].getSource().getNum() == edges[i].getSource().getNum() &&
                                span.edges[j].getDestination().getNum() == edges[i].getDestination().getNum() &&
                                span.edges[j].getWeight()==(edges[i]).getWeight())
                            b=false;
                    }
                    if(b==true) {
                        eArr = Arrays.copyOf(eArr, eArr.length + 1);
                        eArr[eArr.length - 1] = edges[i];
                    }
                }
            }
        Edge min = null;
        Boolean cycle=true;
        while(eArr.length>0 && cycle) {
            cycle=false;
            min = eArr[0];
            int index=0;
            for (int i = 0; i < eArr.length; i++) {
                if (eArr[i].getWeight() < min.getWeight() && !isCreateCycle(span, eArr[i])) {
                    min = eArr[i]; index=i;
                }
            }
            if (isCreateCycle(span, min)){
                cycle=true;
                for(int i=index; i<eArr.length-1;i++)
                    eArr[i]=eArr[i+1];
                eArr = Arrays.copyOf(eArr, eArr.length-1);
            }
        }
        if(eArr.length==0)
            min=null;
        return min;
    }
    private Boolean isCreateCycle(Graph span, Edge edge){
        Boolean first=false;
        Boolean second = false;
        for(int i=0;i<span.countOfEdges;i++){
            if(edge.getSource().getNum() ==span.edges[i].getSource().getNum() ||
                    edge.getSource().getNum() ==span.edges[i].getDestination().getNum()){
                first=true;
            }
            if(edge.getDestination().getNum() == span.edges[i].getSource().getNum() ||
                    edge.getDestination().getNum() == span.edges[i].getDestination().getNum()){
                second=true;}
            if(first==true && second==true)
                return true;
        }
        return false;
    }
    public Graph findSpanningTree(){
        Graph spanningTree = new Graph();
        for(int i=0;i<countOfVertices;i++){
            spanningTree.addVertex(vertices[i].getNum(),vertices[i].getX(),vertices[i].getY());
        }
        Edge first = findFirstMinWeight();
        spanningTree.addEdge(first.getSource(),first.getDestination(), first.getWeight());
        spanningTree.getEdges()[spanningTree.getCountOfEdges()-1].setColor(Color.red);
        Vertex[] arr = new Vertex[2];
        arr[0]=first.getSource();
        arr[1]=first.getDestination();
        while(findMinEdge(arr, spanningTree)!=null) {
            Edge second = findMinEdge(arr, spanningTree);
            spanningTree.addEdge(second.getSource(),second.getDestination(),second.getWeight());
            spanningTree.getEdges()[spanningTree.getCountOfEdges()-1].setColor(Color.red);
            Boolean b1=false;
            Boolean b2=false;
            for(int i=0;i<arr.length;i++){
                if(arr[i].equals(second.getSource()))
                    b1=true;
                if(arr[i].equals(second.getDestination()))
                    b2=true;
            }
            if(b1==false){
                arr = Arrays.copyOf(arr, arr.length+1);
                arr[arr.length-1]=second.getSource();
            }
            if(b2==false){
                arr = Arrays.copyOf(arr, arr.length+1);
                arr[arr.length-1]=second.getDestination();
            }
        }
        return spanningTree;

    }                  //Пошук кістякового дерева
    private Edge[] markAsPassed(Edge[]  passed, Edge edge){
        passed = Arrays.copyOf(passed, passed.length+1);
        passed[passed.length-1]=edge;
        return passed;
    }
    private Vertex[] firstIteration(Graph graph, Vertex first, Edge edge){
        Vertex[] firstVerties = new Vertex[0];
        for(int i=0; i<graph.countOfEdges;i++){
            if(!graph.edges[i].equals(edge)){
            if(first.equals(graph.edges[i].getSource()) ||
                    first.equals(graph.edges[i].getDestination())) {
                firstVerties = Arrays.copyOf(firstVerties, firstVerties.length + 1);
                if (first.equals(graph.edges[i].getSource())) {
                    firstVerties[firstVerties.length - 1] = graph.edges[i].getDestination();
                } else firstVerties[firstVerties.length - 1] = graph.edges[i].getSource();
            }
            }
        }
        return firstVerties;
    }
    private Vertex[] secondIterarion(Graph graph, Vertex[] firstVerties, Edge edge){
        Vertex[] f = firstVerties;
        for(int i=0; i<firstVerties.length;i++){
            for(int j=0; j<graph.countOfEdges;j++){
                if(!graph.edges[i].equals(edge)){
                    if(firstVerties[i].equals(graph.edges[j].getSource()) ||
                            firstVerties[i].equals(graph.edges[j].getDestination())){
                        if(firstVerties[i].equals(graph.edges[j].getSource()))
                            if(!contain(firstVerties,graph.edges[j].getDestination())){
                                firstVerties = Arrays.copyOf(firstVerties, firstVerties.length+1);
                                firstVerties[firstVerties.length-1]=graph.edges[j].getDestination();
                            }
                            else{
                                if(!contain(firstVerties,graph.edges[j].getSource())){
                                    firstVerties = Arrays.copyOf(firstVerties, firstVerties.length+1);
                                    firstVerties[firstVerties.length-1]=graph.edges[j].getSource();
                                }
                            }
                    }
                }
            }
        }
        if(firstVerties.equals(f))
            return null;
        return firstVerties;
    }
    private Boolean contain(Vertex[] firstVerties, Vertex vertex){
        for(int i=0; i<firstVerties.length;i++){
            if(vertex.equals(firstVerties[i]))
                return true;
        }
        return false;
    }
    private Boolean isBridge(Graph graph, Edge edge){
        Vertex first = edge.getSource();
        Vertex second = edge.getDestination();
        Vertex[] firstVerties = firstIteration(graph,first,edge);
        Vertex[] secondVerties =firstIteration(graph,second,edge);
        while(secondIterarion(graph,firstVerties,edge)!=null)
            firstVerties = secondIterarion(graph,firstVerties,edge);
        while(secondIterarion(graph,secondVerties,edge)!=null)
            secondVerties = secondIterarion(graph,secondVerties,edge);
        if(firstVerties.length==0 || secondVerties.length==0)
            return false;
        for(int i=0; i<firstVerties.length;i++){
            for(int j=0; j<secondVerties.length;j++)
                if(firstVerties[i].equals(secondVerties[j]))
                    return false;
        }
        return true;
    }
    private Edge findNextEdge(Graph cycle, Vertex currentVert, Vertex firstVert){
        for(int i=0; i<cycle.countOfEdges;i++){
            if(currentVert.equals(cycle.edges[i].getSource()) ||
                    currentVert.equals(cycle.edges[i].getDestination())){
                if(cycle.edges[i].getSource().equals(firstVert) && cycle.edges.length!=1)
                    i++;
                else{
                    if(!isBridge(cycle, cycle.edges[i])){
                       // System.out.println(isBridge(cycle, cycle.edges[i]));
                        return cycle.edges[i];
                    }
                }
            }
        }
        return null;
    }

    private Edge checkParity(){
        int count=0;
        Vertex first=null;
        for(int i=0;i<countOfVertices;i++){
            if(deg(vertices[i])%2==1) {
                count++;
                if(count==3)
                    return null;
                if(first==null)
                    first=vertices[i];
                else if(deg(vertices[i])<deg(first)){
                    first=vertices[i];
                }
            }
        }
        Edge edge=null;
        if(count==0) edge=edges[0];
        else{
        for(int i=0;i<countOfEdges;i++){
            if(first.equals(edges[i].getSource()) || first.equals(edges[i].getDestination()))
                edge=edges[i];
        }}
        return edge;
    }
    public Edge[] cycleSearch(){
        Edge[] passed = new Edge[0];
        Graph cycle = new Graph();
        cycle.vertices = Arrays.copyOf(vertices,countOfVertices);
        cycle.countOfVertices=countOfVertices;
        cycle.edges = Arrays.copyOf(edges,countOfEdges);
        cycle.countOfEdges=countOfEdges;
        Edge first = checkParity();
        if(first==null){
            System.out.println("Єйлерів цикл або ланцюг не знайдено");
            return null;
        }
        passed = markAsPassed(passed, first);
        cycle.removeEdge(first.getSource(),first.getDestination());
        Vertex currentVert = passed[0].getDestination();
        Edge second = passed[0];
        while(second!=null){
            second=findNextEdge(cycle,currentVert, passed[0].getSource());
            if(second!=null) {
                passed = markAsPassed(passed, second);
                if (currentVert.equals(second.getSource()))
                    currentVert = second.getDestination();
                else currentVert = second.getSource();
                cycle.removeEdge(second.getSource(), second.getDestination());
        }}
        showCycle(passed);
        return passed;
    }
    private void showCycle(Edge[] passed){
        StringBuilder sb = new StringBuilder("Ейлерів цикл/ланцюг:\n");
        Vertex current = passed[0].getSource();
        for(int i=0; i<passed.length;i++){
            Vertex a=null;
            if(!current.equals(passed[i].getSource())){
                a=passed[i].getSource();
            }
            else if(!current.equals(passed[i].getDestination()))
                a=passed[i].getDestination();
            sb.append("y"+i+": "+current.getNum()+a.getNum()+"\n");
            current=a;
        }
        System.out.println(sb);
    }       //Пошук ейлерового циклу/ланцюгу
    private Vertex[] sortByDeg(Vertex[] arr){
        for(int i=arr.length-1; i>0;i--){
            for(int j=0; j<i;j++){
                if(deg(arr[j])<deg(arr[j+1])){
                    Vertex temp=arr[j];
                    arr[j]=arr[j+1];
                    arr[j+1]=temp;
                }
            }
        }
        return arr;
    }
    public void sortByNum(Vertex[] arr){
        for(int i=arr.length-1; i>0;i--){
            for(int j=0; j<i;j++){
                if(arr[j].getNum()>arr[j+1].getNum()){
                    Vertex temp=arr[j];
                    arr[j]=arr[j+1];
                    arr[j+1]=temp;
                }
            }
        }
        for(int i=0; i<arr.length;i++){
            vertices[i]=arr[i];
        }
    }
    private Boolean isAdjacentVertex(Graph graph, Vertex first, Vertex second){
        for(int i=0;i<graph.getCountOfEdges();i++){
            if(first.getNum()==graph.getEdges()[i].getSource().getNum() &&
                    second.getNum()==graph.getEdges()[i].getDestination().getNum())
                return true;
            if(second.getNum() ==graph.getEdges()[i].getSource().getNum() &&
                    first.getNum() ==graph.getEdges()[i].getDestination().getNum())
                return true;
        }
        return false;
    }
    private Boolean isntAdjacentToAllVertex(Graph graph, Vertex second, Color color){
        Vertex[] first = new Vertex[0];
        for(int i=0; i<graph.getCountOfVertices();i++){
            if(graph.getVertices()[i].getColor().equals(color)){
                first = Arrays.copyOf(first, first.length+1);
                first[first.length-1]=graph.getVertices()[i];
            }
        }
        for(int i=0; i<first.length;i++){
            if(isAdjacentVertex(graph, first[i], second)) {
                return false;
            }
        }
        return true;
    }
    private Graph coloredV(Graph graph, Color color){
        Boolean clear=true;
        for(int i=0;i<graph.countOfVertices;i++){
            if(graph.getVertices()[i].getColor()==color) {
                clear = false;
                break;
            }
        }
        if(clear==true)
            for(int i=0; i<graph.countOfVertices; i++){
                if(graph.getVertices()[i].getColor()==Color.white){
                    graph.getVertices()[i].setColor(color);
                    break;
                }
            }
        for(int i=0; i<graph.countOfVertices; i++){
            if(isntAdjacentToAllVertex(graph,graph.getVertices()[i], color)
                    && graph.getVertices()[i].getColor().equals(Color.white)){
                graph.getVertices()[i].setColor(color);
            }
        }
        return graph;
    }
    private Color colorDirectory(int i){
        if(i==0) return Color.orange;
        if(i==1) return Color.green;
        if(i==2) return Color.cyan;
        if(i==3) return Color.pink;
        if(i==4) return Color.magenta;
        if(i==5) return Color.red;
        if(i==6) return Color.blue;
        if(i==7) return Color.orange;
        else{
            Random rand = new Random();
            float r = rand.nextFloat() / 2f + 0.5f;
            float g = rand.nextFloat() / 2f + 0.5f;
            float b = rand.nextFloat() / 2f + 0.5f;
            Color randomColor = new Color(r, g, b);
            return randomColor;
        }
    }
    private Boolean allVertColored(Graph graph){
        for(int i=0; i<graph.countOfVertices;i++){
            if(graph.getVertices()[i].getColor()==Color.white)
                return false;
        }
        return true;
    }
    public Graph colorVerties(){
        Vertex[] v=sortByDeg(getVertices());
        Graph color = new Graph();

        //System.out.println(v.length+" "+countOfVertices);
        for(int i=0; i<v.length;i++){
            color.addVertex(v[i].getNum(),v[i].getX(),v[i].getY());
        }
        for(int i=0;i<countOfEdges;i++){
            color.addEdge(edges[i].getSource(),edges[i].getDestination(),edges[i].getWeight());
        }
        color.countOfEdges=countOfEdges;
        color.countOfVertices=v.length;
        int i=0;
        while(!allVertColored(color)){
            color = coloredV(color, colorDirectory(i));
            i++;
        }
        //color.vertices=color.sortByNum(color.getVertices());
        return color;
    }       //Розфарбування вершин графа
    private Boolean allEdgesColoredOnThisVertex(Graph graph, Vertex vertex){
        for(int i=0; i<graph.getCountOfEdges(); i++){
            if(vertex.getNum() == graph.getEdges()[i].getSource().getNum() ||
                    vertex.getNum() == graph.getEdges()[i].getDestination().getNum()){
                if(graph.getEdges()[i].getColor()==Color.BLACK){
                    return false;
                }
            }
        }
        return true;
    }
    private Boolean canBePaintInThisColor(Graph graph, Vertex first, Edge edge, Color color){
        Boolean r,g,b;
        Vertex second=edge.getSource();
        if(second.getNum() == first.getNum())
            second=edge.getDestination();
        for(int i=0; i<graph.getCountOfEdges(); i++){
            if(first.getNum() == graph.getEdges()[i].getSource().getNum() ||
                    first.getNum() == graph.getEdges()[i].getDestination().getNum()){
                 r=graph.getEdges()[i].getColor().getRed() == color.getRed();
                 g=graph.getEdges()[i].getColor().getGreen() == color.getGreen();
                 b=graph.getEdges()[i].getColor().getBlue() == color.getBlue();
                if (r==true && g==true && b==true){
                    return false;
            }
        }}
        for(int i=0; i<graph.getCountOfEdges(); i++){
            if(!graph.getEdges()[i].equals(edge))
                if(second.getNum() == graph.getEdges()[i].getSource().getNum() ||
                    second.getNum() == graph.getEdges()[i].getDestination().getNum()) {
                     r=graph.getEdges()[i].getColor().getRed() == color.getRed();
                     g=graph.getEdges()[i].getColor().getGreen() == color.getGreen();
                     b=graph.getEdges()[i].getColor().getBlue() == color.getBlue();
                     if (r==true && g==true && b==true){
                         return false;
                }
            }
        }
        return true;
    }
    private Graph coloredE(Graph graph){
        Vertex current=null;
        for(int i=0; i<graph.getCountOfVertices();i++){
            if(!graph.allEdgesColoredOnThisVertex(graph, graph.getVertices()[i])) {
                current = graph.getVertices()[i];
                break;
            }
        }
        if (current==null)
            return graph;
        for(int i=0; i<graph.getCountOfEdges(); i++){
            if(current.getNum() == graph.getEdges()[i].getSource().getNum() ||
                    current.getNum() == graph.getEdges()[i].getDestination().getNum()) {
                if (graph.getEdges()[i].getColor().equals(Color.BLACK)){
                    for(int j=0; j<20 ;j++){
                        if(canBePaintInThisColor(graph,current,graph.getEdges()[i],colorDirectory(j))){
                            graph.getEdges()[i].setColor(colorDirectory(j));
                            break;
                        }
                    }
                }
            }
        }
        return graph;
    }
    public Graph colorEdges(){
        Graph color = new Graph();
        Vertex[] v=sortByDeg(vertices);
        for(int i=0;i<countOfEdges;i++){
            color.addEdge(edges[i].getSource(),edges[i].getDestination(),edges[i].getWeight());
        }
        for(int i=0; i<v.length;i++){
            color.addVertex(v[i].getNum(),v[i].getX(),v[i].getY());
        }
        color.countOfEdges=countOfEdges;
        color.vertices=sortByDeg(color.getVertices());
        color.countOfVertices=countOfVertices;
        //int i=0;
        color = color.coloredE(color);
        color = color.coloredE(color);
        color = color.coloredE(color);
        color = color.coloredE(color);
        return color;
    }       //Розфарбування ребер графа
    public void setDirect(Boolean direct) {
        this.direct = direct;
    }

    public Boolean getDirect() {
        return direct;
    }
    public void showWeight(Boolean b){
        for(int i=0;i<countOfEdges;i++){
            edges[i].setShowWeight(b);
        }
    }
    public Boolean getShowWeight(){
        Boolean b=false;
        if(countOfEdges>0)
            b=edges[0].getShowWeight();
        return b;
    }
    public Vertex[] getVertices() {
        return vertices;
    }

    public Edge[] getEdges() {
        return edges;
    }

    public int getCountOfVertices() {
        return countOfVertices;
    }

    public int getCountOfEdges() {
        return countOfEdges;
    }   //гетери сетери
}