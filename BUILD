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
    manifest_entries = [
        "Gerrit-PluginName: evict-cache",
        "Gerrit-Module: com.ericsson.gerrit.plugins.evictcache.Module",
        "Gerrit-HttpModule: com.ericsson.gerrit.plugins.evictcache.HttpModule",
        "Implementation-Title: evict-cache plugin",
        "Implementation-URL: https://gerrit-review.googlesource.com/#/admin/projects/plugins/evict-cache",
    ],
    resources = glob(["src/main/resources/**/*"]),
)

UTILS = ["src/test/java/com/ericsson/gerrit/plugins/evictcache/Constants.java"]

java_library(
    name = "testutils",
    srcs = UTILS,
)

junit_tests(
    name = "evict_cache_tests",
    srcs = glob(
        ["src/test/java/**/*.java"],
        exclude = UTILS,
    ),
    tags = ["evict-cache"],
    deps = PLUGIN_DEPS + PLUGIN_TEST_DEPS + [
        ":evict-cache__plugin",
        ":testutils",
        "@mockito//jar",
        "@wiremock//jar",
    ],
)
