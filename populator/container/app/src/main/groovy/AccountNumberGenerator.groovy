private static def generateChecksum(id) {
    Integer MAGIC_NUMBER_GC = 1
    String s = String.format("%o%o", MAGIC_NUMBER_GC, id)
    int sum = 0
    for (int i = 0; i < s.length(); i++) {
        sum += s.charAt(i) * (i + 1)
    }
    return sum % 99
}

private static def generateAccountNumber(id) {
    Integer MAGIC_NUMBER_GAN = 2
    return String.format("%05d%02d%02d", id, generateChecksum(id), MAGIC_NUMBER_GAN)
}

generateAccountNumber(id)
