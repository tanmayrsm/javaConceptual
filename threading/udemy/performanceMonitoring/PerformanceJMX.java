package udemy.performanceMonitoring;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class PerformanceJMX {

    public static void main(String[] args) throws Exception {
        // Get the platform Thread MXBean
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();

        // Register the MBean
        ManagementFactory.getPlatformMBeanServer().registerMBean(threadMXBean, null);

        // Your application code here...

        // Sleep or perform other operations to keep the application running
        Thread.sleep(Long.MAX_VALUE);
    }
}
