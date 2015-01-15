package emailmanager;


import java.util.*;

public class MessageData {

    private static final String DEFAULT_EMAIL = "Chris@redwich.ac.uk";
    private static final String DEFAULT_SUBJECT = "no subject";

    private static class Item {

        Item(String t, String m) {
            subject = DEFAULT_SUBJECT;
            from = DEFAULT_EMAIL;
            to = t;
            message = m;
        }

        Item(String s, String t, String m) {
            from = DEFAULT_EMAIL;
            subject = s;
            to = t;
            message = m;
        }

        Item(String f, String s, String t, String m) {
            from = f;
            subject = s;
            to = t;
            message = m;
        }

        Item(String f, String s, String t, String m, int p) {
            from = f;
            subject = s;
            to = t;
            message = m;
            priority = p;
        }

        // instance variables
        private String subject;
        private String from;
        private String to;
        private String message;
        private String label = "";
        private int priority;

        public String toString() {
            return " " + pad(stars(priority), 8) + " " + pad(from, 30) + " " + pad(label, 6) + " " + subject;
        }

        private String pad(String string, int width) {
            width -= string.length();
            for (int i = 0; i < width; ++i) {
                string += " ";
            }
            return string;
        }
    }

    // with a Map you use put to insert a key, value pair
    // and get(key) to retrieve the value associated with a key
    // You don't need to understand how this works!
    private static Map<String, Item> library = new TreeMap<String, Item>();


    static {
        // if you want to have extra library items, put them in here
        // use the same style - keys should be 2 digit Strings
        library.put("01", new Item("Chris@redwich.ac.uk", "Hello",
                "Kate@redwich.ac.uk", "How is the course going?", 2));
        library.put("02", new Item("Kate@redwich.ac.uk", "Re: Hello",
                "Chris@redwich.ac.uk", "> How is the course going?\n\nBrilliant, thanks. The students are all fantastic and are going to get top marks in their coursework.", 2));
        library.put("03", new Item("A.Friend@hmail.com", "Coffee",
                "Chris@redwich.ac.uk", "You're working too hard - fancy meeting for coffee?.", 5));
        library.put("04", new Item("Chris@redwich.ac.uk", "Exam",
                "Asif@redwich.ac.uk", "I have nearly finished writing the exam - I hope the students have revised hard.", 4));
        library.put("05", new Item("A.Student@redwich.ac.uk", "Timetable",
                "Chris@redwich.ac.uk", "help!!! my timetable is rubbish - i cant understand it!!! what r u going to do?", 0));
        library.put("06", new Item("Chris@redwich.ac.uk", "Re: Timetable",
                "A.Student@redwich.ac.uk", "Please ignore the timetables on the portal - just follow the advice on TeachMat.", 0));
        library.put("07", new Item("A.Student@redwich.ac.uk", "Re: Timetable",
                "Chris@redwich.ac.uk", "thx :)", 0));
    }

    private static String listHeader() {
        String output = "Id Priority From                           Label  Subject\n";
        output += "== ======== ====                           =====  =======\n";
        return output;
    }

    public static String listAll() {
        String output = listHeader();
        Iterator iterator = library.keySet().iterator();
        while (iterator.hasNext()) {
            String id = (String) iterator.next();
            Item item = library.get(id);
            output += id + item + "\n";
        }
        return output;
    }

    static String listLabelled(String label) {
        String output = listHeader();
        Iterator iterator = library.keySet().iterator();
        while (iterator.hasNext()) {
            String id = (String) iterator.next();
            Item item = library.get(id);
            if (!item.label.equals(label)) {
                continue;
            }
            output += id + item + "\n";
        }
        return output;
    }

    public static String getSubject(String id) {
        Item item = library.get(id);
        if (item == null) {
            return null; // null means no such item
        } else {
            return item.subject;
        }
    }

    public static String getSender(String id) {
        Item item = library.get(id);
        if (item == null) {
            return null; // null means no such item
        } else {
            return item.from;
        }
    }

    public static int getPriority(String id) {
        Item item = library.get(id);
        if (item == null) {
            return -1; // negative quantity means no such item
        } else {
            return item.priority;
        }
    }

    public static void setPriority(String id, int priority) {
        Item item = library.get(id);
        if (item != null) {
            item.priority = priority;
        }
    }

    static String getMessage(String id) {
        Item item = library.get(id);
        if (item == null) {
            return null; // null means no such item
        } else {
            return item.message;
        }
    }

    static String getRecipient(String id) {
        Item item = library.get(id);
        if (item == null) {
            return null; // null means no such item
        } else {
            return item.to;
        }
    }

    static String setLabel(String id, String label) {
        Item item = library.get(id);
        if (item == null) {
            return null; // null means no such item
        } else {
            item.label = label;
        }
        return listLabelled(label);
    }

    public static String stars(int rating) {
        String stars = "";
        for (int i = 0; i < rating; ++i) {
            stars += "*";
        }
        return stars;
    }
                
    public static void close() {
        // Does nothing for this static version.
        // Write a statement to close the database when you are using one
    }
}