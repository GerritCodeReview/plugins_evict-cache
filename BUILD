load("//tools/bzl:junit.bzl", "junit_tests")
load(
    "//tools/bzl:plugin.bzl",
    "gerrit_plugin",
    "PLUGIN_DEPS",
    "PLUGIN_TEST_DEPS",
)

gerrit_plugin(
    name = "evict-cache",
    srcs = glob(["src/main/java/**/*.java"]),
    resources = glob(["src/main/resources/**/*"]),
    manifest_entries = [
        "Gerrit-PluginName: evict-cache",
        "Gerrit-Module: com.ericsson.gerrit.plugins.evictcache.Module",
        "Gerrit-HttpModule: com.ericsson.gerrit.plugins.evictcache.HttpModule",
        "Implementation-Title: evict-cache plugin",
        "Implementation-URL: https://gerrit-review.googlesource.com/#/admin/projects/plugins/evict-cache",
    ],
)

junit_tests(
    name = "evict_cache_tests",
    srcs = glob(["src/test/java/**/*.java"]),
    tags = ["evict-cache"],
    deps = PLUGIN_DEPS + PLUGIN_TEST_DEPS + [
        ":evict-cache__plugin",
        "@mockito//jar",
        "@wiremock//jar",
    ],
)
