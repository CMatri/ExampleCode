import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.exceptions.JumblrException;
import com.tumblr.jumblr.types.Blog;
import com.tumblr.jumblr.types.Post;
import com.tumblr.jumblr.types.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    BufferedReader br;

    public Main() throws InterruptedException {
        br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Which account (1 or 2): ");
        int acc = Integer.valueOf(getInput());

        String clientStr1 = acc == 1 ? "tmoCoOasRG3XMuxn3cyxxxSrW4vhyLix7LUAJ6vajGkqmsUECE" : "mAhtohzzIE1Ziqn6rRUjvVUBX824E8CfouxRaj7ToWEwiqM2kx";
        String clientStr2 = acc == 1 ? "RmHZVndRZukgp4eBISWstEFdltM0nXQnWzNndqtxq0JGEJL2l3" : "TzhzMzjH05ydDhXDoMJK78rVoVQ81BqCXeLQgDa7rAg4OuAmvB";
        String tokenStr1 = acc == 1 ? "VmnaxyFBjU5M72IKnFcj9qVMSaA3nNMvBt2bV7XssbTQtLu52N" : "VmnaxyFBjU5M72IKnFcj9qVMSaA3nNMvBt2bV7XssbTQtLu52N";
        String tokenStr2 = acc == 1 ? "zDLJd6PPJZCBbMLNA24XIRAjvzgEzrCMrqsHgZVwqeJ18ej3bX" : "zDLJd6PPJZCBbMLNA24XIRAjvzgEzrCMrqsHgZVwqeJ18ej3bX";

        JumblrClient client = new JumblrClient(clientStr1, clientStr2);
        client.setToken(tokenStr1, tokenStr2);

        User user = client.user();
        System.out.println("Hello " + user.getName() + ".");

        Blog blog = new Blog();

        System.out.print("Blog Name: ");
        try {
            blog = client.blogInfo(getInput());
        } catch (JumblrException e) {
            System.out.println("Blog not found... either enter the blog's name or in 'blog.tumblr.com' format.");
            Thread.sleep(3000);
            System.exit(0);
        }

        System.out.print("Post ID: ");
        String name = getInput();
        System.out.println("Searching for post '" + name + "' on blog '" + blog.getName() + "'.");

        Post toReblog = null;

        for (Post p : blog.posts()) {
            if (String.valueOf(p.getId()).contains(name)) {
                System.out.println("Found match: " + p.getPostUrl());
                toReblog = p;
            }
        }

        if (toReblog == null) {
            System.out.println("Post not found on blog '" + blog.getName() + "'... Try entering correct ID.");
            Thread.sleep(3000);
            System.exit(0);
        }

        System.out.print("Reblog how many times (0 to go until post limit): ");
        int choice = Integer.valueOf(getInput());

        if (choice != 0) {
            for (int i = 0; i < choice; i++) {
                try {
                    client.postReblog(user.getBlogs().get(0).getName(), toReblog.getId(), toReblog.getReblogKey());
                } catch (NullPointerException e) {
                    System.out.println("Reblogging index " + (i + 1) + ".");
                    continue;
                } catch (JumblrException e) {
                    System.out.println("Well shit Dalton you exceeded your post limit for today. :/ \nRun mah program again tomorrow. You can kik me or something if you have any other problems.");
                    Thread.sleep(5000);
                    System.exit(0);
                }
            }
        } else {
            for (int i = 0; i < 665655; i++) {
                try {
                    client.postReblog(user.getBlogs().get(0).getName(), toReblog.getId(), toReblog.getReblogKey());
                } catch (NullPointerException e) {
                    System.out.println("Reblogging index " + (i + 1) + ".");
                    continue;
                } catch (JumblrException e) {
                    System.out.println("Well shit Dalton you exceeded your post limit for today. :/ \nRun mah program again tomorrow. You can kik me or something if you have any other problems.");
                    Thread.sleep(5000);
                    System.exit(0);
                }
            }
        }

        System.out.println("Finished. Press enter to exit...");
        getInput();
    }

    public String getInput() {
        try {
            return br.readLine();
        } catch (IOException e) {
            return "";
        }
    }

    public static void main(String[] args) {
        try {
            new Main();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
