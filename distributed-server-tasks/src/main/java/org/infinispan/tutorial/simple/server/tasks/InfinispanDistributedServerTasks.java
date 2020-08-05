package org.infinispan.tutorial.simple.server.tasks;

import org.infinispan.client.hotrod.DefaultTemplate;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.client.hotrod.impl.ConfigurationProperties;
import org.infinispan.commons.api.CacheContainerAdmin;

import java.util.HashMap;
import java.util.Map;


public class InfinispanDistributedServerTasks {

   public static void main(String[] args) throws Exception {
      ConfigurationBuilder builder = new ConfigurationBuilder();
      builder.addServer()
            .host("127.0.0.1")
            .port(ConfigurationProperties.DEFAULT_HOTROD_PORT)
            .security().authentication()
            .username("username")
            .password("password")
            .realm("default");
      RemoteCacheManager cacheManager = new RemoteCacheManager(builder.build());

      RemoteCache<String, String> cache =
          cacheManager.administration()
              .withFlags(CacheContainerAdmin.AdminFlag.VOLATILE)
              .getOrCreateCache("data", DefaultTemplate.DIST_SYNC);

      // Create task parameters
      Map<String, String> parameters = new HashMap<>();
      parameters.put("greetee", "developer");

      // Execute hello task
      cache.execute("dist-greet", parameters);

      // Stop the cache manager and release all resources
      cacheManager.stop();
   }

}
