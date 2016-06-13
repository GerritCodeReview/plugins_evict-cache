// Copyright (C) 2015 Ericsson
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.ericsson.gerrit.plugins.evictcache;

import com.google.common.cache.RemovalNotification;
import com.google.gerrit.server.cache.CacheRemovalListener;
import com.google.inject.Inject;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.regex.Pattern;

class EvictCacheHandler<K, V> implements CacheRemovalListener<K, V> {
  private final ScheduledThreadPoolExecutor executor;
  private final RestSession restSession;
  private final Pattern pattern;

  @Inject
  EvictCacheHandler(RestSession restSession,
      @EvictCacheExecutor ScheduledThreadPoolExecutor executor) {
    this.restSession = restSession;
    this.executor = executor;
    pattern = Pattern.compile(
        "(^accounts.*|^groups.*|ldap_groups|ldap_usernames|^project.*|sshkeys|web_sessions)");
  }

  @Override
  public void onRemoval(String pluginName, String cacheName,
      RemovalNotification<K, V> notification) {
    if (!Context.isForwardedEvent() && !notification.wasEvicted()
        && isValidCacheName(cacheName)) {
      executor.execute(
          new EvictCacheTask(pluginName, cacheName, notification.getKey()));
    }
  }

  private boolean isValidCacheName(String cacheName) {
    return pattern.matcher(cacheName).matches();
  }

  class EvictCacheTask implements Runnable {
    private String pluginName;
    private String cacheName;
    private Object key;

    EvictCacheTask(String pluginName, String cacheName, Object key) {
      this.pluginName = pluginName;
      this.cacheName = cacheName;
      this.key = key;
    }

    @Override
    public void run() {
      restSession.evict(pluginName, cacheName, key);
    }
  }
}
