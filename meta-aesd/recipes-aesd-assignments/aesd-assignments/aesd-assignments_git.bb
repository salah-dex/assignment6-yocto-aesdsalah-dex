LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://github.com/salah-dex/assignment6-aesdsalah-dex.git;protocol=https;branch=Master \
           file://aesd-assignments-init"
SRCREV = "9f79cdd20eda2160fcfdcf94d5dd3bea0b0e8178"

PV = "1.0+git${SRCPV}"

S = "${WORKDIR}/git/server"

TARGET_LDFLAGS += "-pthread -lrt"

inherit update-rc.d

INITSCRIPT_NAME = "aesd-assignments"
INITSCRIPT_PARAMS = "start 99 2 3 4 5 . stop 19 0 1 6 ."

do_configure() {
    :
}

do_compile() {
    oe_runmake
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/aesdsocket ${D}${bindir}/aesdsocket

    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/aesd-assignments-init ${D}${sysconfdir}/init.d/aesd-assignments
}

FILES:${PN} += "${bindir}/aesdsocket"
FILES:${PN} += "${sysconfdir}/init.d/aesd-assignments"