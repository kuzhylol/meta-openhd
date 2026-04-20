SUMMARY = "QOpenHD ground station application"
DESCRIPTION = "Qt-based ground station UI for OpenHD"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ccabeb20df52b9236fcc6ea3d7e6f55"

SRC_URI = "gitsm://github.com/OpenHD/QOpenHD.git;branch=release;protocol=https \
           file://qopenhd.service \
           file://0001-yocto-linux-target.patch \
           "

SRCREV = "3d3250168fedbf70deff77e9764dbdb5353163de"

S = "${WORKDIR}/git"
QMAKE_PROFILES = "${S}/QOpenHD.pro"
EXTRA_QMAKEVARS_PRE += "CONFIG-=EnableSpeech"

inherit qmake5 pkgconfig systemd

DEPENDS += "\
    ffmpeg \
    gstreamer1.0 \
    gstreamer1.0-plugins-base \
    libdrm \
    qtbase \
    qtcharts \
    qtdeclarative \
    qtlocation \
    qttools-native \
    mavlink-headers \
"

RDEPENDS:${PN} += "\
    ffmpeg \
    fontconfig \
    gstreamer1.0 \
    gstreamer1.0-libav \
    gstreamer1.0-plugins-bad \
    gstreamer1.0-plugins-base \
    gstreamer1.0-plugins-good \
    gstreamer1.0-plugins-ugly \
    libdrm \
    qtbase \
    qtbase-plugins \
    qtcharts \
    qtcharts-qmlplugins \
    qtdeclarative \
    qtdeclarative-qmlplugins \
    qtgraphicaleffects-qmlplugins \
    qtlocation \
    qtquickcontrols \
    qtquickcontrols2 \
"

EXTRA_QMAKEVARS_PRE += " \
    CONFIG+=kms \
    QMAKE_CXXFLAGS+=-Wno-address-of-packed-member \
    QMAKE_CXXFLAGS+=-Wno-cast-align \
    QMAKE_CXXFLAGS+=-Wno-unused-function \
    QMAKE_CXXFLAGS+=-Wno-unused-variable \
    QMAKE_CXXFLAGS+=-Wno-unused-parameter \
    QMAKE_CXXFLAGS+=-Wno-sign-compare \
"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${B}/release/QOpenHD ${D}${bindir}/qopenhd
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${UNPACKDIR}/qopenhd.service ${D}${systemd_system_unitdir}
}

do_install:append:rpi() {
    install -d ${D}${datadir}/qopenhd
    install -m 0644 ${S}/rpi_qt_eglfs_kms_config.json ${D}${datadir}/qopenhd
}

SYSTEMD_SERVICE:${PN} = "qopenhd.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"

FILES:${PN} += "\
    ${bindir}/qopenhd \
    ${systemd_system_unitdir}/qopenhd.service \
    ${datadir}/qopenhd/rpi_qt_eglfs_kms_config.json \
"
