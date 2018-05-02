package au.edu.cdu.common.util;

import org.junit.Assert;
import org.junit.Test;

public class QueueTest {
    @Test
    public void testQueue() {
        Queue q = new Queue(3);
        int a;
        boolean empty;

        empty = q.isEmpty();
        Assert.assertTrue(empty);
        q.add(1);
        empty = q.isEmpty();
        Assert.assertTrue(!empty);
        q.add(2);
        empty = q.isEmpty();
        Assert.assertTrue(!empty);
        q.add(3);
        empty = q.isEmpty();
        Assert.assertTrue(!empty);
        q.add(4);
        empty = q.isEmpty();
        Assert.assertTrue(!empty);
        a = q.poll();
        Assert.assertEquals(1, a);
        empty = q.isEmpty();
        Assert.assertTrue(!empty);
        q.add(5);
        empty = q.isEmpty();
        Assert.assertTrue(!empty);
        q.add(6);
        empty = q.isEmpty();
        Assert.assertTrue(!empty);
        a = q.poll();
        Assert.assertEquals(2, a);
        empty = q.isEmpty();
        Assert.assertTrue(!empty);
        q.add(7);
        a = q.poll();
        Assert.assertEquals(3, a);
        empty = q.isEmpty();
        Assert.assertTrue(!empty);
        a = q.poll();
        Assert.assertEquals(4, a);
        a = q.poll();
        Assert.assertEquals(5, a);
        a = q.poll();
        Assert.assertEquals(6, a);
        a = q.poll();
        Assert.assertEquals(7, a);
        empty = q.isEmpty();
        Assert.assertTrue(empty);
    }
}
