import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

class application {

    private static class Node {
        String data;
        Node next;

        Node(String data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node top;

    public application() {
        this.top = null;
    }

    public boolean isEmpty() {
        return (top == null);
    }

    public void push(String value) {
        Node newNode = new Node(value);
        newNode.next = top;
        top = newNode;
    }

    public String pop() {
        if (isEmpty()) return null;
        String poppedValue = top.data;
        top = top.next;
        return poppedValue;
    }

    public String peek() {
        if (isEmpty()) return null;
        return top.data;
    }

    public static void main(String[] args) throws IOException {
   
        String portEnv = System.getenv("PORT");
        int port = (portEnv != null) ? Integer.parseInt(portEnv) : 8080;

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new MyHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Server started on port " + port);
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            application history = new application();
            StringBuilder response = new StringBuilder();

            response.append("Current Page: google.com\n");
            history.push("google.com");
            
            response.append("Visited: github.com\n");
            history.push("github.com");
            
            response.append("Visited: stackoverflow.com\n\n");
            history.push("stackoverflow.com");

            response.append("Clicking Back Button\n");
            if (!history.isEmpty()) {
                history.pop();
                response.append("Returned to: ").append(history.peek()).append("\n");
            }

            response.append("\nClicking Back Button\n");
            if (!history.isEmpty()) {
                history.pop();
                response.append("Returned to: ").append(history.peek()).append("\n");
            }

            byte[] responseBytes = response.toString().getBytes();
            t.getResponseHeaders().set("Content-Type", "text/plain");
            t.sendResponseHeaders(200, responseBytes.length);
            OutputStream os = t.getResponseBody();
            os.write(responseBytes);
            os.close();
        }
    }
}
