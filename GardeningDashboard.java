 
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class GardeningDashboard extends JFrame {

    private final Color darkGreen = new Color(0, 55, 39);
    private final Color green = new Color(20, 115, 69);
    private final Color lightGreen = new Color(235, 247, 236);
    private final Color background = new Color(246, 249, 246);
    private JPanel content;

    public GardeningDashboard() {
        setTitle("Online Gardening & Community Platform");
        setSize(1450, 900);
        setMinimumSize(new Dimension(1050, 700));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(background);

        createSidebar();
        createDashboard();

        setVisible(true);
    }

    // LEFT SIDEBAR
    private void createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(250, 0));
        sidebar.setBackground(darkGreen);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(28, 18, 20, 18));

        JLabel logo = new JLabel("🌿 GardenHub");
        logo.setFont(new Font("SansSerif", Font.BOLD, 25));
        logo.setForeground(Color.WHITE);
        sidebar.add(logo);

        JLabel slogan = new JLabel("       Grow Together");
        slogan.setForeground(new Color(190, 230, 195));
        slogan.setFont(new Font("SansSerif", Font.ITALIC, 14));
        sidebar.add(slogan);

        sidebar.add(Box.createVerticalStrut(45));

        addMenu(sidebar, "⌂   Dashboard", "dashboard");
        addMenu(sidebar, "❧   Explore Plants", "plants");
        addMenu(sidebar, "♧   Community", "community");
        addMenu(sidebar, "✿   Gardening Tips", "tips");
        addMenu(sidebar, "♣   My Garden", "garden");

        sidebar.add(Box.createVerticalGlue());

        JLabel footer = new JLabel(
                "<html><i>Small steps grow into<br>big green dreams. 🌱</i></html>"
        );
        footer.setForeground(new Color(200, 230, 205));
        footer.setFont(new Font("SansSerif", Font.PLAIN, 15));
        footer.setBorder(new EmptyBorder(10, 5, 15, 5));
        sidebar.add(footer);

        add(sidebar, BorderLayout.WEST);
    }

    private void addMenu(JPanel sidebar, String text, String page) {
        JButton button = new JButton(text);
        button.setMaximumSize(new Dimension(220, 52));
        button.setPreferredSize(new Dimension(220, 52));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFont(new Font("SansSerif", Font.PLAIN, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(darkGreen);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(0, 14, 0, 5));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addActionListener(e -> {
            switch (page) {
                case "dashboard":
                    createDashboard();
                    break;
                case "plants":
                    showPlants();
                    break;
                case "community":
                    showCommunity();
                    break;
                case "tips":
                    showTips();
                    break;
                case "garden":
                    showGarden();
                    break;
            }
        });

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(new Color(40, 125, 75));
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(darkGreen);
            }
        });

        sidebar.add(button);
        sidebar.add(Box.createVerticalStrut(10));
    }

    // MAIN DASHBOARD
    private void createDashboard() {
        content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(background);
        content.setBorder(new EmptyBorder(0, 22, 22, 22));

        // Welcome banner
        JPanel hero = new GradientPanel();
        hero.setLayout(new BorderLayout());
        hero.setPreferredSize(new Dimension(1000, 155));
        hero.setMaximumSize(new Dimension(Integer.MAX_VALUE, 155));
        hero.setBorder(new EmptyBorder(25, 28, 25, 28));

        JPanel welcome = new JPanel();
        welcome.setOpaque(false);
        welcome.setLayout(new BoxLayout(welcome, BoxLayout.Y_AXIS));

        JLabel greeting = new JLabel("Good Morning, Gardener! 🌿");
        greeting.setFont(new Font("SansSerif", Font.BOLD, 27));
        greeting.setForeground(darkGreen);

        JLabel description = new JLabel(
                "Discover plants, share ideas, and grow your own green space."
        );
        description.setFont(new Font("SansSerif", Font.PLAIN, 15));
        description.setForeground(new Color(55, 80, 70));

        welcome.add(greeting);
        welcome.add(Box.createVerticalStrut(12));
        welcome.add(description);

        JLabel profile = new JLabel(
                "<html><b>👤 Guest User</b><br>Gardener</html>"
        );
        profile.setFont(new Font("SansSerif", Font.PLAIN, 15));
        profile.setForeground(darkGreen);

        hero.add(welcome, BorderLayout.WEST);
        hero.add(profile, BorderLayout.EAST);

        content.add(hero);
        content.add(Box.createVerticalStrut(20));

        // Four statistic cards
        JPanel stats = new JPanel(new GridLayout(1, 4, 14, 0));
        stats.setOpaque(false);

        stats.add(statCard("🌱", "Available Plants", "120+", "Explore varieties",
                new Color(226, 246, 224)));
        stats.add(statCard("👥", "Community Members", "850+", "Connect with gardeners",
                new Color(225, 241, 255)));
        stats.add(statCard("💡", "Gardening Tips", "45+", "Learn something new",
                new Color(255, 246, 214)));
        stats.add(statCard("📝", "Active Posts", "32+", "Share your ideas",
                new Color(243, 229, 255)));

        stats.setPreferredSize(new Dimension(1000, 135));
        stats.setMaximumSize(new Dimension(Integer.MAX_VALUE, 135));

        content.add(stats);
        content.add(Box.createVerticalStrut(20));

        // Three dashboard columns
        JPanel body = new JPanel(new GridLayout(1, 3, 16, 0));
        body.setOpaque(false);

        body.add(featuredPlants());
        body.add(communityHighlights());
        body.add(quickTips());

        body.setPreferredSize(new Dimension(1000, 390));
        body.setMaximumSize(new Dimension(Integer.MAX_VALUE, 390));

        content.add(body);
        content.add(Box.createVerticalStrut(18));

        // Bottom inspirational banner
        JPanel banner = new GradientPanel();
        banner.setLayout(new BorderLayout());
        banner.setBorder(new EmptyBorder(24, 28, 24, 28));
        banner.setPreferredSize(new Dimension(1000, 105));
        banner.setMaximumSize(new Dimension(Integer.MAX_VALUE, 105));

        JLabel message = new JLabel(
                "<html><b>🌱 Let's make the world greener!</b><br>" +
                "<font size='4'>Gardening is not just a hobby; it's a way of life.</font></html>"
        );
        message.setForeground(Color.WHITE);
        message.setFont(new Font("SansSerif", Font.BOLD, 20));
        banner.add(message, BorderLayout.CENTER);

        content.add(banner);

        showContent();
    }

    // STATISTIC CARD
    private JPanel statCard(String icon, String title, String number,
                            String subtitle, Color iconColor) {
        JPanel card = new JPanel(new BorderLayout(12, 8));
        card.setBackground(Color.WHITE);
        card.setBorder(new CompoundBorder(
                new LineBorder(new Color(220, 234, 222), 1, true),
                new EmptyBorder(17, 14, 17, 14)
        ));

        JLabel symbol = new JLabel(icon, SwingConstants.CENTER);
        symbol.setFont(new Font("SansSerif", Font.PLAIN, 28));
        symbol.setOpaque(true);
        symbol.setBackground(iconColor);
        symbol.setPreferredSize(new Dimension(53, 53));

        JPanel details = new JPanel();
        details.setOpaque(false);
        details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));

        JLabel name = new JLabel(title);
        name.setFont(new Font("SansSerif", Font.PLAIN, 13));
        name.setForeground(darkGreen);

        JLabel value = new JLabel(number);
        value.setFont(new Font("SansSerif", Font.BOLD, 28));
        value.setForeground(green);

        JLabel sub = new JLabel(subtitle);
        sub.setFont(new Font("SansSerif", Font.PLAIN, 11));
        sub.setForeground(Color.GRAY);

        details.add(name);
        details.add(Box.createVerticalStrut(5));
        details.add(value);
        details.add(Box.createVerticalStrut(5));
        details.add(sub);

        card.add(symbol, BorderLayout.WEST);
        card.add(details, BorderLayout.CENTER);
        return card;
    }

    // FEATURED PLANTS
    private JPanel featuredPlants() {
        JPanel panel = sectionPanel("🌿  Featured Plants");
        panel.setLayout(new BorderLayout(8, 12));

        JPanel grid = new JPanel(new GridLayout(2, 2, 10, 10));
        grid.setOpaque(false);

        grid.add(plantCard("🪴", "Aloe Vera", "Indoor plant", "Easy to grow"));
        grid.add(plantCard("🌹", "Rose", "Flowering plant", "Needs sunlight"));
        grid.add(plantCard("🌿", "Money Plant", "Indoor climber", "Low maintenance"));
        grid.add(plantCard("🍃", "Tulsi", "Herbal plant", "Regular watering"));

        panel.add(grid, BorderLayout.CENTER);
        return panel;
    }

    private JPanel plantCard(String emoji, String name, String type, String care) {
        JPanel card = new JPanel();
        card.setBackground(new Color(250, 253, 249));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new CompoundBorder(
                new LineBorder(new Color(225, 235, 225), 1, true),
                new EmptyBorder(12, 9, 12, 9)
        ));

        JLabel image = new JLabel(emoji, SwingConstants.CENTER);
        image.setFont(new Font("SansSerif", Font.PLAIN, 43));
        image.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel(name);
        title.setFont(new Font("SansSerif", Font.BOLD, 14));
        title.setForeground(darkGreen);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel category = new JLabel(type);
        category.setFont(new Font("SansSerif", Font.PLAIN, 11));
        category.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel careLabel = new JLabel("☀ " + care);
        careLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        careLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton details = new JButton("View Details");
        details.setBackground(green);
        details.setForeground(Color.WHITE);
        details.setFocusPainted(false);
        details.setFont(new Font("SansSerif", Font.PLAIN, 11));
        details.setAlignmentX(Component.CENTER_ALIGNMENT);

        details.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        name + "\n" + type + "\nCare: " + care,
                        "Plant Details", JOptionPane.INFORMATION_MESSAGE));

        card.add(image);
        card.add(Box.createVerticalStrut(6));
        card.add(title);
        card.add(Box.createVerticalStrut(5));
        card.add(category);
        card.add(Box.createVerticalStrut(5));
        card.add(careLabel);
        card.add(Box.createVerticalGlue());
        card.add(Box.createVerticalStrut(10));
        card.add(details);

        return card;
    }

    // COMMUNITY SECTION
    private JPanel communityHighlights() {
        JPanel panel = sectionPanel("♧  Community Highlights");
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        addCommunityPost(panel, "👩", "Priya Sharma",
                "My tomato plants are growing beautifully! 🍅", "12 likes");
        addCommunityPost(panel, "👨", "Rahul Verma",
                "Which plants are best for a small balcony?", "8 likes");
        addCommunityPost(panel, "👩", "Anjali Singh",
                "Share your tips for healthy indoor plants.", "15 likes");

        JButton createPost = new JButton("✎  Create a Post");
        createPost.setBackground(green);
        createPost.setForeground(Color.WHITE);
        createPost.setFocusPainted(false);
        createPost.setAlignmentX(Component.CENTER_ALIGNMENT);

        createPost.addActionListener(e -> {
            JTextField author = new JTextField();
            JTextArea message = new JTextArea(4, 20);
            message.setLineWrap(true);
            message.setWrapStyleWord(true);

            JPanel form = new JPanel(new GridLayout(0, 1, 5, 5));
            form.add(new JLabel("Your name:"));
            form.add(author);
            form.add(new JLabel("Your post:"));
            form.add(new JScrollPane(message));

            int result = JOptionPane.showConfirmDialog(
                    this, form, "Create a Community Post",
                    JOptionPane.OK_CANCEL_OPTION
            );

            if (result == JOptionPane.OK_OPTION
                    && !author.getText().trim().isEmpty()
                    && !message.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Post created by " + author.getText().trim()
                                + "!\n\n" + message.getText().trim(),
                        "Community", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        panel.add(Box.createVerticalGlue());
        panel.add(createPost);
        return panel;
    }

    private void addCommunityPost(JPanel panel, String avatar, String name,
                                 String message, String likes) {
        JPanel post = new JPanel();
        post.setLayout(new BorderLayout(8, 5));
        post.setOpaque(false);
        post.setBorder(new EmptyBorder(12, 2, 12, 2));

        JLabel icon = new JLabel(avatar);
        icon.setFont(new Font("SansSerif", Font.PLAIN, 27));

        JPanel text = new JPanel();
        text.setOpaque(false);
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));

        JLabel author = new JLabel(name);
        author.setFont(new Font("SansSerif", Font.BOLD, 13));
        author.setForeground(darkGreen);

        JTextArea postText = new JTextArea(message);
        postText.setFont(new Font("SansSerif", Font.PLAIN, 12));
        postText.setLineWrap(true);
        postText.setWrapStyleWord(true);
        postText.setEditable(false);
        postText.setOpaque(false);
        postText.setFocusable(false);

        JLabel likeLabel = new JLabel("♥  " + likes);
        likeLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));
        likeLabel.setForeground(green);

        text.add(author);
        text.add(Box.createVerticalStrut(6));
        text.add(postText);
        text.add(Box.createVerticalStrut(6));
        text.add(likeLabel);

        post.add(icon, BorderLayout.WEST);
        post.add(text, BorderLayout.CENTER);

        panel.add(post);
        panel.add(new JSeparator());
    }

    // QUICK TIPS SECTION
    private JPanel quickTips() {
        JPanel panel = sectionPanel("💡  Quick Tips");
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        String[] tips = {
                "Water plants according to their needs.",
                "Provide suitable sunlight.",
                "Use good-quality soil and compost.",
                "Remove dry and damaged leaves.",
                "Check plants regularly for pests.",
                "Choose pots with drainage holes."
        };

        for (int i = 0; i < tips.length; i++) {
            JPanel row = new JPanel(new BorderLayout(10, 5));
            row.setOpaque(false);
            row.setBorder(new EmptyBorder(9, 0, 9, 0));

            JLabel number = new JLabel(String.valueOf(i + 1),
                    SwingConstants.CENTER);
            number.setOpaque(true);
            number.setBackground(new Color(215, 241, 213));
            number.setForeground(green);
            number.setFont(new Font("SansSerif", Font.BOLD, 13));
            number.setPreferredSize(new Dimension(28, 28));

            JTextArea tip = new JTextArea(tips[i]);
            tip.setWrapStyleWord(true);
            tip.setLineWrap(true);
            tip.setEditable(false);
            tip.setFocusable(false);
            tip.setOpaque(false);
            tip.setFont(new Font("SansSerif", Font.PLAIN, 12));
            tip.setForeground(new Color(50, 70, 60));

            row.add(number, BorderLayout.WEST);
            row.add(tip, BorderLayout.CENTER);
            panel.add(row);
        }

        panel.add(Box.createVerticalGlue());
        return panel;
    }

    // REUSABLE SECTION PANEL
    private JPanel sectionPanel(String title) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(new CompoundBorder(
                new LineBorder(new Color(222, 234, 223), 1, true),
                new EmptyBorder(15, 14, 15, 14)
        ));

        JLabel heading = new JLabel(title);
        heading.setFont(new Font("SansSerif", Font.BOLD, 17));
        heading.setForeground(darkGreen);
        heading.setBorder(new EmptyBorder(0, 0, 12, 0));

        panel.add(heading);
        return panel;
    }

    // OTHER NAVIGATION PAGES
    private void showPlants() {
        JPanel page = sectionPanel("🌿 Explore Plants");
        page.setLayout(new GridLayout(2, 2, 15, 15));

        page.add(plantCard("🪴", "Aloe Vera", "Indoor plant", "Easy to grow"));
        page.add(plantCard("🌹", "Rose", "Flowering plant", "Needs sunlight"));
        page.add(plantCard("🌿", "Money Plant", "Indoor climber", "Low maintenance"));
        page.add(plantCard("🍃", "Tulsi", "Herbal plant", "Regular watering"));

        setPage(page);
    }

    private void showCommunity() {
        JPanel page = sectionPanel("♧ Gardening Community");
        page.setLayout(new BoxLayout(page, BoxLayout.Y_AXIS));

        addCommunityPost(page, "👩", "Priya Sharma",
                "My tomato plants are growing beautifully!", "12 likes");
        addCommunityPost(page, "👨", "Rahul Verma",
                "Which plants are best for a small balcony?", "8 likes");
        addCommunityPost(page, "👩", "Anjali Singh",
                "Share your tips for healthy indoor plants.", "15 likes");

        JButton post = new JButton("Create a Post");
        post.addActionListener(e -> JOptionPane.showMessageDialog(
                this, "Use the dashboard to create a community post."));

        page.add(post);
        setPage(page);
    }

    private void showTips() {
        JPanel page = sectionPanel("💡 Gardening Tips");
        page.setLayout(new BoxLayout(page, BoxLayout.Y_AXIS));

        String[] tips = {
                "Water plants according to their needs.",
                "Provide suitable sunlight.",
                "Use compost to improve soil quality.",
                "Remove dry and damaged leaves.",
                "Check for pests regularly.",
                "Use pots with drainage holes."
        };

        for (String tip : tips) {
            JLabel label = new JLabel("  🌱  " + tip);
            label.setFont(new Font("SansSerif", Font.PLAIN, 16));
            label.setBorder(new EmptyBorder(15, 5, 15, 5));
            page.add(label);
        }

        setPage(page);
    }

    private void showGarden() {
        JPanel page = sectionPanel("♣ My Garden");
        page.setLayout(new BoxLayout(page, BoxLayout.Y_AXIS));

        JLabel description = new JLabel(
                "Your personal garden starts here! 🌱");
        description.setFont(new Font("SansSerif", Font.BOLD, 19));

        JButton addPlant = new JButton("+ Add a Plant");
        addPlant.addActionListener(e -> {
            String name = JOptionPane.showInputDialog(
                    this, "Enter the name of your plant:");

            if (name != null && !name.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        name.trim() + " added to your garden!");
            }
        });

        page.add(description);
        page.add(Box.createVerticalStrut(25));
        page.add(new JLabel("Keep track of the plants you grow."));
        page.add(Box.createVerticalStrut(15));
        page.add(addPlant);

        setPage(page);
    }

    private void setPage(JPanel page) {
        content.removeAll();
        content.setLayout(new BorderLayout());
        content.add(page, BorderLayout.CENTER);
        showContent();
    }

    private void showContent() {
        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(18);

        getContentPane().removeAll();
        // Rebuild the sidebar and main content layout.
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(250, 0));
        sidebar.setBackground(darkGreen);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(28, 18, 20, 18));

        JLabel logo = new JLabel("🌿 GardenHub");
        logo.setFont(new Font("SansSerif", Font.BOLD, 25));
        logo.setForeground(Color.WHITE);
        sidebar.add(logo);

        JLabel slogan = new JLabel("       Grow Together");
        slogan.setForeground(new Color(190, 230, 195));
        sidebar.add(slogan);
        sidebar.add(Box.createVerticalStrut(40));

        addMenu(sidebar, "⌂   Dashboard", "dashboard");
        addMenu(sidebar, "❧   Explore Plants", "plants");
        addMenu(sidebar, "♧   Community", "community");
        addMenu(sidebar, "✿   Gardening Tips", "tips");
        addMenu(sidebar, "♣   My Garden", "garden");

        sidebar.add(Box.createVerticalGlue());

        JLabel footer = new JLabel(
                "<html><i>Small steps grow into<br>big green dreams.</i></html>");
        footer.setForeground(Color.WHITE);
        sidebar.add(footer);

        getContentPane().add(sidebar, BorderLayout.WEST);
        getContentPane().add(scroll, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    // Gradient background panel
    private static class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();

            GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(218, 243, 215),
                    getWidth(), getHeight(), new Color(248, 252, 244)
            );

            g2.setPaint(gradient);
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GardeningDashboard dashboard = new GardeningDashboard();
            dashboard.createDashboard();
        });
    }
}
