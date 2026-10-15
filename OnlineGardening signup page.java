 
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class OnlineGardening {

    private static final String URL =
            "jdbc:mysql://localhost:3306/gardening_db";
    private static final String DB_USER = "root";

    // Replace with your own MySQL password
    private static final String DB_PASSWORD = "YOUR_PASSWORD";

    private static int currentUserId;
    private static String currentUserName;

    static Connection connect() throws SQLException {
        return DriverManager.getConnection(
                URL, DB_USER, DB_PASSWORD);
    }

    static String hashPassword(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(
                password.getBytes(StandardCharsets.UTF_8));

        StringBuilder result = new StringBuilder();
        for (byte b : hash) {
            result.append(String.format("%02x", b & 0xff));
        }
        return result.toString();
    }

    static void message(String text) {
        JOptionPane.showMessageDialog(null, text);
    }

    // ---------------- LOGIN PAGE ----------------

    static void showLogin() {
        JFrame frame = new JFrame("GardenConnect - Login");
        frame.setSize(430, 330);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 12));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        panel.setBackground(new Color(230, 245, 230));

        JLabel title = new JLabel("GardenConnect");
        title.setFont(new Font("Arial", Font.BOLD, 22));

        JTextField email = new JTextField();
        JPasswordField password = new JPasswordField();

        JButton login = new JButton("Login");
        JButton register = new JButton("Sign Up");

        panel.add(title);
        panel.add(new JLabel("Grow Together!"));
        panel.add(new JLabel("Email:"));
        panel.add(email);
        panel.add(new JLabel("Password:"));
        panel.add(password);
        panel.add(login);
        panel.add(register);

        login.addActionListener(e -> {
            try (Connection con = connect();
                 PreparedStatement ps = con.prepareStatement(
                    "SELECT id, name FROM users " +
                    "WHERE email=? AND password_hash=?")) {

                ps.setString(1, email.getText().trim());
                ps.setString(2, hashPassword(
                        new String(password.getPassword())));

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        currentUserId = rs.getInt("id");
                        currentUserName = rs.getString("name");
                        frame.dispose();
                        showDashboard();
                    } else {
                        message("Invalid email or password.");
                    }
                }
            } catch (Exception ex) {
                message("Login error: " + ex.getMessage());
            }
        });

        register.addActionListener(e -> {
            frame.dispose();
            showRegister();
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    // ---------------- SIGN UP PAGE ----------------

    static void showRegister() {
        JFrame frame = new JFrame("Create Account");
        frame.setSize(450, 370);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 12));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        panel.setBackground(new Color(230, 245, 230));

        JTextField name = new JTextField();
        JTextField email = new JTextField();
        JPasswordField password = new JPasswordField();

        JButton signup = new JButton("Create Account");
        JButton back = new JButton("Back to Login");

        panel.add(new JLabel("Full Name:"));
        panel.add(name);
        panel.add(new JLabel("Email:"));
        panel.add(email);
        panel.add(new JLabel("Password (8+ characters):"));
        panel.add(password);
        panel.add(signup);
        panel.add(back);

        signup.addActionListener(e -> {
            String n = name.getText().trim();
            String em = email.getText().trim();
            String pw = new String(password.getPassword());

            if (n.isEmpty() || em.isEmpty() || pw.length() < 8
                    || !em.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
                message("Enter a valid name, email and password " +
                        "of at least 8 characters.");
                return;
            }

            try (Connection con = connect();
                 PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO users(name,email,password_hash) " +
                    "VALUES(?,?,?)")) {

                ps.setString(1, n);
                ps.setString(2, em);
                ps.setString(3, hashPassword(pw));
                ps.executeUpdate();

                message("Account created successfully! Please log in.");
                frame.dispose();
                showLogin();

            } catch (SQLIntegrityConstraintViolationException ex) {
                message("This email is already registered.");
            } catch (Exception ex) {
                message("Registration error: " + ex.getMessage());
            }
        });

        back.addActionListener(e -> {
            frame.dispose();
            showLogin();
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    // ---------------- DASHBOARD ----------------

    static void showDashboard() {
        JFrame frame = new JFrame("GardenConnect Dashboard");
        frame.setSize(650, 430);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(5, 1, 12, 12));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 70, 25, 70));
        panel.setBackground(new Color(230, 245, 230));

        JLabel title = new JLabel(
                "Welcome, " + currentUserName + "!",
                SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 25));

        JButton care = new JButton("Plant Care Guide");
        JButton garden = new JButton("My Garden");
        JButton community = new JButton("Gardening Community");
        JButton logout = new JButton("Logout");

        panel.add(title);
        panel.add(care);
        panel.add(garden);
        panel.add(community);
        panel.add(logout);

        care.addActionListener(e -> showPlantCare());
        garden.addActionListener(e -> showGarden());
        community.addActionListener(e -> showCommunity());

        logout.addActionListener(e -> {
            frame.dispose();
            showLogin();
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    // ---------------- PLANT CARE GUIDE ----------------

    static void showPlantCare() {
        JTextArea area = new JTextArea(
                "PLANT CARE GUIDE\n\n" +
                "Tomato\nSunlight: 6-8 hours\n" +
                "Water: Keep soil evenly moist\n\n" +
                "Rose\nSunlight: Around 6 hours\n" +
                "Water: Water at the soil level\n\n" +
                "Mint\nSunlight: Partial to full sun\n" +
                "Water: Keep soil moist\n\n" +
                "Money Plant\nSunlight: Bright indirect light\n" +
                "Water: When the top soil starts drying\n\n" +
                "Sunflower\nSunlight: 6-8 hours\n" +
                "Water: Regularly, especially when young");

        area.setEditable(false);
        area.setFont(new Font("Arial", Font.PLAIN, 16));

        JOptionPane.showMessageDialog(null,
                new JScrollPane(area),
                "Plant Care", JOptionPane.INFORMATION_MESSAGE);
    }

    // ---------------- MY GARDEN ----------------

    static void showGarden() {
        JFrame frame = new JFrame("My Garden");
        frame.setSize(550, 400);
        frame.setLocationRelativeTo(null);

        JTextArea area = new JTextArea();
        area.setEditable(false);

        JButton add = new JButton("Add Plant");
        JButton refresh = new JButton("Refresh");

        JPanel buttons = new JPanel();
        buttons.add(add);
        buttons.add(refresh);

        Runnable loadPlants = () -> {
            area.setText("MY PLANTS\n\n");

            try (Connection con = connect();
                 PreparedStatement ps = con.prepareStatement(
                    "SELECT plant_name, watering, sunlight " +
                    "FROM plants WHERE user_id=? ORDER BY id DESC")) {

                ps.setInt(1, currentUserId);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        area.append("Plant: " +
                                rs.getString("plant_name") +
                                "\nWatering: " +
                                rs.getString("watering") +
                                "\nSunlight: " +
                                rs.getString("sunlight") +
                                "\n\n");
                    }
                }
            } catch (Exception ex) {
                message("Error loading plants: " + ex.getMessage());
            }
        };

        add.addActionListener(e -> {
            JTextField plant = new JTextField();
            JTextField water = new JTextField("Daily / As needed");
            JTextField sun = new JTextField("Partial / Full sun");

            Object[] fields = {
                "Plant Name:", plant,
                "Watering:", water,
                "Sunlight:", sun
            };

            int result = JOptionPane.showConfirmDialog(
                    frame, fields, "Add a Plant",
                    JOptionPane.OK_CANCEL_OPTION);

            if (result != JOptionPane.OK_OPTION) return;

            if (plant.getText().trim().isEmpty()) {
                message("Plant name is required.");
                return;
            }

            try (Connection con = connect();
                 PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO plants " +
                    "(user_id,plant_name,watering,sunlight) " +
                    "VALUES(?,?,?,?)")) {

                ps.setInt(1, currentUserId);
                ps.setString(2, plant.getText().trim());
                ps.setString(3, water.getText().trim());
                ps.setString(4, sun.getText().trim());
                ps.executeUpdate();

                message("Plant added to your garden!");
                loadPlants.run();

            } catch (Exception ex) {
                message("Error adding plant: " + ex.getMessage());
            }
        });

        refresh.addActionListener(e -> loadPlants.run());

        frame.add(new JScrollPane(area), BorderLayout.CENTER);
        frame.add(buttons, BorderLayout.SOUTH);

        loadPlants.run();
        frame.setVisible(true);
    }

    // ---------------- COMMUNITY POSTS ----------------

    static void showCommunity() {
        JFrame frame = new JFrame("Gardening Community");
        frame.setSize(600, 500);
        frame.setLocationRelativeTo(null);

        JTextArea posts = new JTextArea();
        posts.setEditable(false);
        posts.setFont(new Font("Arial", Font.PLAIN, 15));

        JTextArea newPost = new JTextArea(3, 30);
        JButton publish = new JButton("Publish Post");
        JButton refresh = new JButton("Refresh Posts");

        JPanel bottom = new JPanel(new BorderLayout(5, 5));
        bottom.add(new JScrollPane(newPost), BorderLayout.CENTER);

        JPanel actions = new JPanel();
        actions.add(publish);
        actions.add(refresh);
        bottom.add(actions, BorderLayout.SOUTH);

        Runnable loadPosts = () -> {
            posts.setText("GARDENING COMMUNITY\n\n");

            String sql =
                "SELECT users.name, posts.content, posts.created_at " +
                "FROM posts JOIN users ON posts.user_id=users.id " +
                "ORDER BY posts.id DESC";

            try (Connection con = connect();
                 PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    posts.append(rs.getString("name") + "\n");
                    posts.append(rs.getString("content") + "\n");
                    posts.append("Posted: " +
                            rs.getTimestamp("created_at") + "\n");
                    posts.append("--------------------------\n");
                }

            } catch (Exception ex) {
                message("Error loading posts: " + ex.getMessage());
            }
        };

        publish.addActionListener(e -> {
            String content = newPost.getText().trim();

            if (content.isEmpty()) {
                message("Please write something first.");
                return;
            }

            try (Connection con = connect();
                 PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO posts(user_id,content) VALUES(?,?)")) {

                ps.setInt(1, currentUserId);
                ps.setString(2, content);
                ps.executeUpdate();

                newPost.setText("");
                loadPosts.run();
                message("Your post has been published!");

            } catch (Exception ex) {
                message("Error publishing post: " + ex.getMessage());
            }
        });

        refresh.addActionListener(e -> loadPosts.run());

        frame.add(new JScrollPane(posts), BorderLayout.CENTER);
        frame.add(bottom, BorderLayout.SOUTH);

        loadPosts.run();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(OnlineGardening::showLogin);
    }
}