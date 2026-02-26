SUMMARY = "Realtek RTL8812AU WiFi driver"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b234ee4d69f5fce4486a80fdaf4a4263"

inherit module

SRC_URI = "git://github.com/OpenHD/rtl8812au.git;branch=v5.6.4.2;protocol=https"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"
PV = "5.6.4.2-git"

EXTRA_OEMAKE:append = " KSRC=${STAGING_KERNEL_BUILDDIR}"

do_configure:append() {
    sed -i 's/^CONFIG_PLATFORM_I386_PC *= *y/CONFIG_PLATFORM_I386_PC = n/' ${S}/Makefile
}

do_configure:append:raspberrypi4_64() {
    sed -i 's/^CONFIG_PLATFORM_ARM64_RPI *= *n/CONFIG_PLATFORM_ARM64_RPI = y/' ${S}/Makefile
}
