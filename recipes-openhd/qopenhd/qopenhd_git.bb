SUMMARY = "QOpenHD ground station application"
DESCRIPTION = "Qt-based ground station UI for OpenHD"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ccabeb20df52b9236fcc6ea3d7e6f55"

SRC_URI = "gitsm://github.com/OpenHD/QOpenHD.git;branch=2.7-evo;protocol=https \
           file://0001-yocto-linux-target.patch \
           "

SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

inherit qmake5 pkgconfig

DEPENDS += "\
    gstreamer1.0 \
    gstreamer1.0-libav \
    gstreamer1.0-plugins-base \
    gstreamer1.0-plugins-good \
    libdrm \
    mavlink-headers \
    qtbase \
    qtbase-native \
    qtcharts \
    qtdeclarative \
    qttools-native \
"

RDEPENDS:${PN} += "\
    qtquickcontrols \
    qtcharts \
    gstreamer1.0 \
    gstreamer1.0-plugins-base \
    gstreamer1.0-plugins-good \
    gstreamer1.0-plugins-bad \
    gstreamer1.0-libav \
"

EXTRA_QMAKEVARS_PRE += " \
    QMAKE_CXXFLAGS+=-Wno-address-of-packed-member \
    QMAKE_CXXFLAGS+=-Wno-cast-align \
    QMAKE_CXXFLAGS+=-Wno-unused-function \
    QMAKE_CXXFLAGS+=-Wno-unused-variable \
    QMAKE_CXXFLAGS+=-Wno-unused-parameter \
    QMAKE_CXXFLAGS+=-Wno-sign-compare \
"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/../build/release/QOpenHD ${D}${bindir}/qopenhd
}

FILES:${PN} += "${bindir}/qopenhd"
