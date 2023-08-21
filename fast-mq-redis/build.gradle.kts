dependencies {
    implementation("io.netty:netty-resolver-dns:4.1.94.Final")
    implementation ("io.lettuce:lettuce-core:6.2.5.RELEASE")
    implementation ("org.msgpack:msgpack-core:0.9.5")
    implementation ("org.slf4j:slf4j-api:2.0.5")
    implementation(project(":fast-mq-api"))
}