package com.common.network

import com.common.common.ext.logE
import okhttp3.*
import java.security.KeyManagementException
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager


private fun OkHttpClient.Builder.enableTls12(trustManager: X509TrustManager) = apply {
    try {
        val sslContext = SSLContext.getInstance(TlsVersion.TLS_1_2.javaName())
        sslContext.init(null, arrayOf(trustManager), null)

        sslSocketFactory(
            Tls12SocketFactory(
                sslContext.socketFactory
            ), trustManager)
    } catch (e: Exception) {
        logE<OkHttpClient>("Error while setting TLS 1.2 compatibility")
    }
}

internal fun OkHttpClient.Builder.applyTrustManagerOkHttp(): OkHttpClient.Builder {
    return try {
        val trustManagerFactory =
            TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(null as KeyStore?)

        val trustManagers = trustManagerFactory.trustManagers
        if (trustManagers.size != 1 || trustManagers[0] !is X509TrustManager) {
            throw IllegalStateException(
                "Unexpected default trust managers:" + Arrays.toString(
                    trustManagers
                )
            )
        }
        val trustManager = trustManagerFactory.trustManagers
            .first { it is X509TrustManager } as X509TrustManager

        val sslContext = SSLContext.getInstance(TlsVersion.TLS_1_2.javaName())
        sslContext.init(null, arrayOf(trustManager), null)
        this.enableTls12(trustManager)

        this.applyConnectionSpecsOkHttp()
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
        this
    } catch (e: KeyManagementException) {
        e.printStackTrace()
        this
    } catch (e: KeyStoreException) {
        e.printStackTrace()
        this
    }
}

private fun OkHttpClient.Builder.applyConnectionSpecsOkHttp(): OkHttpClient.Builder {
    val listChipper = listOf(
        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
        CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256,
        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA,
        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA,
        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA,
        CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA,
        CipherSuite.TLS_ECDHE_ECDSA_WITH_RC4_128_SHA,
        CipherSuite.TLS_ECDHE_RSA_WITH_RC4_128_SHA,
        CipherSuite.TLS_DHE_RSA_WITH_AES_128_CBC_SHA,
        CipherSuite.TLS_DHE_DSS_WITH_AES_128_CBC_SHA,
        CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA,
        CipherSuite.TLS_RSA_WITH_AES_128_CBC_SHA,
        CipherSuite.TLS_RSA_WITH_AES_256_CBC_SHA
    )

    val cipherSuites = mutableListOf<CipherSuite>()
    ConnectionSpec.MODERN_TLS.cipherSuites()?.forEach {
        cipherSuites.add(it)
    }

    listChipper.forEach {
        if (it !in cipherSuites) {
            cipherSuites.add(it)
        }
    }

    val cs = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
        .supportsTlsExtensions(true)
        .tlsVersions(TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0)
        .cipherSuites(*cipherSuites.toTypedArray())
        .build()

    val specs = mutableListOf<ConnectionSpec>().apply {
        add(cs)
        add(ConnectionSpec.COMPATIBLE_TLS)
        add(ConnectionSpec.CLEARTEXT)
    }

    return connectionSpecs(specs)
}

internal fun createCertificatePinier(): CertificatePinner {
    val host = "*.*.*"
    return CertificatePinner.Builder()
        .build()
}