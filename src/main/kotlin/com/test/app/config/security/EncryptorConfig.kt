package com.test.app.config.security

import org.jasypt.encryption.StringEncryptor
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig
import org.jasypt.salt.StringFixedSaltGenerator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

private const val PASSWORD  = "EMART"
private const val ALGORITHM = "PBEWithMD5AndDES"
private const val SALT_KEY = "EMART-SALT"

val encryptor = PooledPBEStringEncryptor().apply {

    val config = SimpleStringPBEConfig().apply {
        password = PASSWORD
        algorithm = ALGORITHM
        providerName = "SunJCE"
        keyObtentionIterations = 1000
        poolSize = 5
        saltGenerator = StringFixedSaltGenerator(SALT_KEY)
        stringOutputType = "hexadecimal"
    }

    setConfig(config)
}

@Configuration
class EncryptorConfig {

    @Bean("jasyptStringEncryptor")
    fun encryptor(): StringEncryptor = encryptor

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder = BCryptPasswordEncoder()
}
