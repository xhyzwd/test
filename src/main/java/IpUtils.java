/**
 * @author xhy
 * @Classname
 * @Description
 * @date 2021/2/3 - 16:13
 */

import com.googlecode.ipv6.IPv6Network;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ipv4工具类
 * xhy
 * 2019.11.16
 * 20201203新增ipv6支持
 */
public class IpUtils {

    /**
     * 在main方法里面可以测试下
     * 主要功能都在main方法里，需要什么自己找
     */
    public static void main(String[] args) {
        String t = "2409:8004:801:200::/56";
        IPv6Network network = IPv6Network.fromString(t);
        String ipv4 = "1.1.1.1/31";
        System.out.println(getBeginIpv6Str(t));
        System.out.println(getEndIpv6Str(t));
//        System.out.println(getBeginIpStr("1.1.1.1", "32"));
//        System.out.println(getEndIpStr("1.1.1.1", "32"));
//        System.out.println(network.getFirst().toLongString());
//        System.out.println(network.getLast().toLongString());
//        System.out.println(hasSameIp("1.1.1.1/31","1.1.1.1/30"));
//        System.out.println(isSameAddress("1.1.1.1/30","1.1.1.2/30"));
//        System.out.println(isIpv4("255.1.1.22"));
//        System.out.println(isIpv4("255.1.1.22/42"));
//        System.out.println("****************");
//        System.out.println(isSameAddress("1.1.1.44","192.168.9.46"));
//        String ip="1.1.1.129";//ip
//        String mask="25";//位数，如果只知道子网掩码不知道位数的话在参考getMaskMap()方法
//        System.out.println("");
//        String ipStr = toRange("1.1.1.129/25");
//        System.out.println(hasSameIp("",ipStr));
//        System.out.println("*****"+ipStr);
//        //获得起始IP和终止IP的方法（包含网络地址和广播地址）
//        String startIp=getBeginIpStr(ip, mask);
//        String endIp=getEndIpStr(ip, mask);
//        System.out.println("起始IP：" + startIp + "终止IP：" + endIp);
//
//        //获得起始IP和终止IP的方法（不包含网络地址和广播地址）
//        String subStart=startIp.split("\\.")[0]+"."+startIp.split("\\.")[1]+"."+startIp.split("\\.")[2]+".";
//        String subEnd=endIp.split("\\.")[0]+"."+endIp.split("\\.")[1]+"."+endIp.split("\\.")[2]+".";
//        startIp=subStart+(Integer.parseInt(startIp.split("\\.")[3])+1);
//        endIp=subEnd+(Integer.parseInt(endIp.split("\\.")[3])-1);
//        System.out.println("起始IP：" + startIp + "终止IP：" + endIp);
//
//        //判断一个IP是否属于某个网段
//        boolean flag = isInRange("10.2.0.0", "10.3.0.0/17");
//        System.out.println(flag);
//
//        //根据位数查询IP数量
//        int ipCount = getIpCount("8");
//        System.out.println(ipCount);
//
//        //判断是否是一个IP
//        System.out.println(isIP("192.168.1.0"));
//
//        //把ip转换为数字(mysql中inet_aton()的实现)
//        System.out.println(ipToDouble("192.168.1.1"));

        //打印IP段所有IP（IP过多会内存溢出）
//      List<String> list = parseIpMaskRange(ip, mask);
//      for (String s : list){
//          System.out.println(s);
//      }
    }

    public static final String DEFAULT_SUBNET_MASK_A = "255.0.0.0";
    public static final String DEFAULT_SUBNET_MASK_B = "255.255.0.0";
    public static final String DEFAULT_SUBNET_MASK_C = "255.255.255.0";
    public static final String TYPE_IP_A = "A";
    public static final String TYPE_IP_B = "B";
    public static final String TYPE_IP_C = "C";
    public static final String TYPE_IP_D = "D";
    public static final String TYPE_IP_LOCATE = "locate";
    /**
     * 根据 ipv6/掩码位 计算IP段的起始IP 如 IP串2409:8004:801:200::/56
     *
     * @param ipAddress
     *            给定的IP地址段，如2409:8004:801:200::/56
     * @return 起始IP的字符串表示
     */
    public static String getBeginIpv6Str(String ipAddress) {
        IPv6Network network = IPv6Network.fromString(ipAddress);
        return network.getFirst().toLongString();
    }
    /**
     * 根据 ipv6/掩码位 计算IP段的结束IP 如 IP串2409:8004:801:200::/56
     *
     * @param ipAddress
     *            给定的IP地址段，如2409:8004:801:200::/56
     * @return 起始IP的字符串表示
     */
    public static String getEndIpv6Str(String ipAddress) {
        IPv6Network network = IPv6Network.fromString(ipAddress);
        return network.getLast().toLongString();
    }
    //判断ip是否属于同一网段，默认子网掩码
    public static boolean isSameAddress(String resourceIp, String requestIp) {
        if (getIpType(resourceIp).equals(getIpType(requestIp))){
            return isSameAddress(resourceIp,requestIp,getIpDefaultMask(getIpType(resourceIp)));
        }
        return false;
    }
    //通过ip类型，获取默认IP子网掩码
    private static String getIpDefaultMask(String ipType){
        switch (ipType){
            case TYPE_IP_A:return DEFAULT_SUBNET_MASK_A;
            case TYPE_IP_B:return DEFAULT_SUBNET_MASK_B;
            case TYPE_IP_C:return DEFAULT_SUBNET_MASK_C;
            default:return ("没有对应的mask地址");
        }
    }
    //判断ip是否属于同一网段
    public static boolean isSameAddress(String resourceIp, String requestIp, String subnetMask) {
        String resourceAddr = getAddrIp(resourceIp, subnetMask);
        String subnetMaskAddr = getAddrIp(requestIp, subnetMask);
        if (resourceAddr.equals(subnetMaskAddr)) {
            return true;
        }
        return false;
    }
    //获取ip的二进制字符串
    private static String getBinaryIp(String data) {
        String[] datas = data.split("\\.");
        String binaryIp = "";
        for (String ipStr : datas) {
            long signIp = Long.parseLong(ipStr);
            String binary = Long.toBinaryString(signIp);
            long binaryInt = Long.parseLong(binary);
            binaryIp += String.format("%08d", binaryInt);
        }
        return binaryIp;
    }
    //获取ip的地址位
    private static String getAddrIp(String ip, String subnetMask) {
        StringBuilder addrIp = new StringBuilder();
        String binaryIp = getBinaryIp(ip);
        String binarySubnetMask = getBinaryIp(subnetMask);
        for (int i = 0; i < 32; i++) {
            byte ipByte = Byte.parseByte(String.valueOf(binaryIp.charAt(i)));
            byte subnetMaskByte = Byte.parseByte(String.valueOf(binarySubnetMask.charAt(i)));
            addrIp.append(ipByte & subnetMaskByte);
        }
        return addrIp.toString();
    }
    //获取ip是什么类型，ABCD
    public static String getIpType(String ip) {
        String binaryIp = getBinaryIp(ip);
        if (ip.startsWith("127")){
            return TYPE_IP_LOCATE;
        }
        if (binaryIp.startsWith("0")){
            return TYPE_IP_A;
        }
        if (binaryIp.startsWith("10")){
            return TYPE_IP_B;
        }
        if (binaryIp.startsWith("110")){
            return TYPE_IP_C;
        }
        if (binaryIp.startsWith("1110")){
            return TYPE_IP_D;
        }
        return "无效ip异常";
    }

    public static boolean isIpv4(String ipAddress) {
        String[] ipAndMask;
        String adress;
        if(ipAddress.indexOf("/")>-1){
            ipAndMask = ipAddress.split("/");
            String maskReg = "^[1-9]|(1\\d)|(2\\d)|(3[0-2])$";
            Pattern pattern = Pattern.compile(maskReg);
            Matcher matcher = pattern.matcher(ipAndMask[1]);
            if(!matcher.matches()){
                return false;
            }
            adress = ipAndMask[0];
        }else{
            adress = ipAddress;
        }
        String ip = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
        Pattern pattern = Pattern.compile(ip);
        Matcher matcher = pattern.matcher(adress);
        return matcher.matches();
    }

    /**
     * 功能：判断一个IP是不是在一个网段下的
     * 格式：isInRange("192.168.8.3", "192.168.9.10/22");
     */
    public static boolean isInRange(String ip, String cidr) {
        String[] ips = ip.split("\\.");
        int ipAddr = (Integer.parseInt(ips[0]) << 24)
                | (Integer.parseInt(ips[1]) << 16)
                | (Integer.parseInt(ips[2]) << 8) | Integer.parseInt(ips[3]);
        int type = Integer.parseInt(cidr.replaceAll(".*/", ""));
        int mask = 0xFFFFFFFF << (32 - type);
        String cidrIp = cidr.replaceAll("/.*", "");
        String[] cidrIps = cidrIp.split("\\.");
        int cidrIpAddr = (Integer.parseInt(cidrIps[0]) << 24)
                | (Integer.parseInt(cidrIps[1]) << 16)
                | (Integer.parseInt(cidrIps[2]) << 8)
                | Integer.parseInt(cidrIps[3]);

        return (ipAddr & mask) == (cidrIpAddr & mask);
    }

    public static boolean hasSameIp(String ip1, String ip2) {
        String[] ips1 = ip1.split(",");
        String[] ips2 = ip2.split(",");

        for (int i = 0 ; i < ips1.length ; i++) {
            if(ips1[i].indexOf("/")>-1){
                ips1[i] = IpUtils.toRange(ips1[i]);
            }
            String[] parts = ips1[i].split("\\-");
            format(parts);
            if (parts.length == 1) {
                ips1[i] = parts[0];
            } else {
                ips1[i] = parts[0] + "-" + parts[1];
            }
        }

        for (int i = 0 ; i < ips2.length ; i++) {
            if(ips2[i].indexOf("/")>-1){
                ips2[i] = IpUtils.toRange(ips2[i]);
            }
            String[] parts = ips2[i].split("\\-");
            format(parts);
            if (parts.length == 1) {
                ips2[i] = parts[0];
            } else {
                ips2[i] = parts[0] + "-" + parts[1];
            }
        }

        for (String _ip1 : ips1) {
            if (_ip1.contains("-")) {
                if (hasSameIp2(_ip1.substring(0, _ip1.indexOf("-")), _ip1.substring(_ip1.indexOf("-")+1), ips2)) {
                    return true;
                }
            } else if (hasSameIp1(_ip1, ips2)) {
                return true;
            }
        }
        for (String _ip2 : ips2) {
            if (_ip2.contains("-")) {
                if (hasSameIp2(_ip2.substring(0, _ip2.indexOf("-")), _ip2.substring(_ip2.indexOf("-")+1), ips1)) {
                    return true;
                }
            } else if (hasSameIp1(_ip2, ips1)) {
                return true;
            }
        }

        return false;
    }
    private static boolean hasSameIp2(String ip1, String ip2, String[] ips) {
        return hasSameIp1(ip1, ips) || hasSameIp1(ip2, ips);
    }
    private static boolean hasSameIp1(String ip, String[] ips) {
        for (String _ip : ips) {
            if (_ip.contains("-")) {
                String ip1 = _ip.substring(0, _ip.indexOf("-"));
                String ip2 = _ip.substring(_ip.indexOf("-")+1);
                if (ip.equals(ip1) || ip.equals(ip2)) {
                    return true;
                }
                if (ip.compareTo(ip1) > 0 && ip.compareTo(ip2) < 0) {
                    return true;
                }
            } else if (_ip.equals(ip)) {
                return true;
            }
        }
        return false;
    }
    private static void format(String[] parts) {
        for (int i = 0 ; i < parts.length ; i++) {
            String[] a = parts[i].split("\\.");
            for (int j = 0 ; j < a.length ; j++) {
                a[j] = "  "+a[j];
                a[j] = a[j].substring(a[j].length() - 3);
            }
            parts[i] = a[0] + "." + a[1] + "." + a[2] + "." + a[3];
        }
    }

    /**
     * 功能：根据IP和位数返回该IP网段的所有IP
     * 格式：parseIpMaskRange("192.192.192.1.", "23")
     */
    public static List<String> parseIpMaskRange(String ip, String mask){
        List<String> list=new ArrayList<>();
        if ("32".equals(mask)) {
            list.add(ip);
        }else{
            String startIp=getBeginIpStr(ip, mask);
            String endIp=getEndIpStr(ip, mask);
            if (!"31".equals(mask)) {
                String subStart=startIp.split("\\.")[0]+"."+startIp.split("\\.")[1]+"."+startIp.split("\\.")[2]+".";
                String subEnd=endIp.split("\\.")[0]+"."+endIp.split("\\.")[1]+"."+endIp.split("\\.")[2]+".";
                startIp=subStart+(Integer.parseInt(startIp.split("\\.")[3])+1);
                endIp=subEnd+(Integer.parseInt(endIp.split("\\.")[3])-1);
            }
            list=parseIpRange(startIp, endIp);
        }
        return list;
    }

    /**
     * 功能：根据位数返回IP总数
     * 格式：parseIpMaskRange("192.192.192.1", "23")
     */
    public static int getIpCount(String mask) {
        return BigDecimal.valueOf(Math.pow(2, 32 - Integer.parseInt(mask))).setScale(0, BigDecimal.ROUND_DOWN).intValue();//IP总数，去小数点
    }

    public static String toRange(String ip){
        String[] ipMask = ip.split("/");
        StringBuffer range = new StringBuffer();
        range.append(IpUtils.getBeginIpStr(ipMask[0], ipMask[1]));
        range.append("-");
        range.append(IpUtils.getEndIpStr(ipMask[0], ipMask[1]));
        return range.toString();
    }
    /**
     * 功能：根据位数返回IP总数
     * 格式：isIP("192.192.192.1")
     */
    public static boolean isIP(String str) {
        String regex = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(str).matches();
    }

    public static List<String> parseIpRange(String ipfrom, String ipto) {
        List<String> ips = new ArrayList<String>();
        String[] ipfromd = ipfrom.split("\\.");
        String[] iptod = ipto.split("\\.");
        int[] int_ipf = new int[4];
        int[] int_ipt = new int[4];
        for (int i = 0; i < 4; i++) {
            int_ipf[i] = Integer.parseInt(ipfromd[i]);
            int_ipt[i] = Integer.parseInt(iptod[i]);
        }
        for (int A = int_ipf[0]; A <= int_ipt[0]; A++) {
            for (int B = (A == int_ipf[0] ? int_ipf[1] : 0); B <= (A == int_ipt[0] ? int_ipt[1]
                    : 255); B++) {
                for (int C = (B == int_ipf[1] ? int_ipf[2] : 0); C <= (B == int_ipt[1] ? int_ipt[2]
                        : 255); C++) {
                    for (int D = (C == int_ipf[2] ? int_ipf[3] : 0); D <= (C == int_ipt[2] ? int_ipt[3]
                            : 255); D++) {
                        ips.add(A + "." + B + "." + C + "." + D);
                    }
                }
            }
        }
        return ips;
    }

    /**
     * 把long类型的Ip转为一般Ip类型：xx.xx.xx.xx
     *
     * @param ip
     * @return
     */
    public static String getIpFromLong(Long ip)
    {
        String s1 = String.valueOf((ip & 4278190080L) / 16777216L);
        String s2 = String.valueOf((ip & 16711680L) / 65536L);
        String s3 = String.valueOf((ip & 65280L) / 256L);
        String s4 = String.valueOf(ip & 255L);
        return s1 + "." + s2 + "." + s3 + "." + s4;
    }
    /**
     * 把xx.xx.xx.xx类型的转为long类型的
     *
     * @param ip
     * @return
     */
    public static Long getIpFromString(String ip)
    {
        Long ipLong = 0L;
        String ipTemp = ip;
        ipLong = ipLong * 256
                + Long.parseLong(ipTemp.substring(0, ipTemp.indexOf('.')));
        ipTemp = ipTemp.substring(ipTemp.indexOf('.') + 1, ipTemp.length());
        ipLong = ipLong * 256
                + Long.parseLong(ipTemp.substring(0, ipTemp.indexOf('.')));
        ipTemp = ipTemp.substring(ipTemp.indexOf(".") + 1, ipTemp.length());
        ipLong = ipLong * 256
                + Long.parseLong(ipTemp.substring(0, ipTemp.indexOf('.')));
        ipTemp = ipTemp.substring(ipTemp.indexOf('.') + 1, ipTemp.length());
        ipLong = ipLong * 256 + Long.parseLong(ipTemp);
        return ipLong;
    }
    /**
     * 根据掩码位获取掩码
     *
     * @param maskBit
     *            掩码位数，如"28"、"30"
     * @return
     */
    public static String getMaskByMaskBit(String maskBit)
    {
        return "".equals(maskBit) ? "error, maskBit is null !" : getMaskMap(maskBit);
    }

    /**
     * 根据 ip/掩码位 计算IP段的起始IP 如 IP串 218.240.38.69/30
     *
     * @param ip
     *            给定的IP，如218.240.38.69
     * @param maskBit
     *            给定的掩码位，如30
     * @return 起始IP的字符串表示
     */
    public static String getBeginIpStr(String ip, String maskBit)
    {
        return getIpFromLong(getBeginIpLong(ip, maskBit));
    }

    /**
     * 根据 ip 掩码 计算IP段的起始IP 如 IP串 218.240.38.69 255.255.255.0
     *
     * @param ip
     *            给定的IP，如218.240.38.69
     * @param mask
     *            给定的掩码，如255.255.255.0
     * @return 起始IP的字符串表示
     */
    public static String getBeginIpStr2(String ip, String mask)
    {
        return getIpFromLong(getIpFromString(ip) & getIpFromString(mask));
    }

    /**
     * 根据 ip/掩码位 计算IP段的起始IP 如 IP串 218.240.38.69/30
     *
     * @param ip
     *            给定的IP，如218.240.38.69
     * @param maskBit
     *            给定的掩码位，如30
     * @return 起始IP的长整型表示
     */
    public static Long getBeginIpLong(String ip, String maskBit)
    {
        return getIpFromString(ip) & getIpFromString(getMaskByMaskBit(maskBit));
    }
    /**
     * 根据 ip/掩码位 计算IP段的终止IP 如 IP串 218.240.38.69/30
     *
     * @param ip
     *            给定的IP，如218.240.38.69
     * @param maskBit
     *            给定的掩码位，如30
     * @return 终止IP的字符串表示
     */
    public static String getEndIpStr(String ip, String maskBit)
    {
        return getIpFromLong(getEndIpLong(ip, maskBit));
    }

    /**
     * 根据 ip/掩码位 计算IP段的终止IP 如 IP串 218.240.38.69/30
     *
     * @param ip
     *            给定的IP，如218.240.38.69
     * @param maskBit
     *            给定的掩码位，如30
     * @return 终止IP的长整型表示
     */
    public static Long getEndIpLong(String ip, String maskBit)
    {
        return getBeginIpLong(ip, maskBit)
                + ~getIpFromString(getMaskByMaskBit(maskBit));
    }


    /**
     * 根据子网掩码转换为掩码位 如 255.255.255.252转换为掩码位 为 30
     *
     * @param netmarks
     * @return
     */
    public static int getNetMask(String netmarks)
    {
        StringBuilder sbf;
        String str;
        int inetmask = 0;
        int count = 0;
        String[] ipList = netmarks.split("\\.");
        for (int n = 0; n < ipList.length; n++)
        {
            sbf = toBin(Integer.parseInt(ipList[n]));
            str = sbf.reverse().toString();
            count = 0;
            for (int i = 0; i < str.length(); i++)
            {
                i = str.indexOf('1', i);
                if (i == -1)
                {
                    break;
                }
                count++;
            }
            inetmask += count;
        }
        return inetmask;
    }

    /**
     * 计算子网大小
     *
     * @param
     *
     * @return
     */
    public static int getPoolMax(int maskBit)
    {
        if (maskBit <= 0 || maskBit >= 32)
        {
            return 0;
        }
        return (int) Math.pow(2, 32 - maskBit) - 2;
    }
    private static StringBuilder toBin(int x)
    {
        StringBuilder result = new StringBuilder();
        result.append(x % 2);
        x /= 2;
        while (x > 0)
        {
            result.append(x % 2);
            x /= 2;
        }
        return result;
    }

    public static String getMaskMap(String maskBit) {
        if ("1".equals(maskBit)) return "128.0.0.0";
        if ("2".equals(maskBit)) return "192.0.0.0";
        if ("3".equals(maskBit)) return "224.0.0.0";
        if ("4".equals(maskBit)) return "240.0.0.0";
        if ("5".equals(maskBit)) return "248.0.0.0";
        if ("6".equals(maskBit)) return "252.0.0.0";
        if ("7".equals(maskBit)) return "254.0.0.0";
        if ("8".equals(maskBit)) return "255.0.0.0";
        if ("9".equals(maskBit)) return "255.128.0.0";
        if ("10".equals(maskBit)) return "255.192.0.0";
        if ("11".equals(maskBit)) return "255.224.0.0";
        if ("12".equals(maskBit)) return "255.240.0.0";
        if ("13".equals(maskBit)) return "255.248.0.0";
        if ("14".equals(maskBit)) return "255.252.0.0";
        if ("15".equals(maskBit)) return "255.254.0.0";
        if ("16".equals(maskBit)) return "255.255.0.0";
        if ("17".equals(maskBit)) return "255.255.128.0";
        if ("18".equals(maskBit)) return "255.255.192.0";
        if ("19".equals(maskBit)) return "255.255.224.0";
        if ("20".equals(maskBit)) return "255.255.240.0";
        if ("21".equals(maskBit)) return "255.255.248.0";
        if ("22".equals(maskBit)) return "255.255.252.0";
        if ("23".equals(maskBit)) return "255.255.254.0";
        if ("24".equals(maskBit)) return "255.255.255.0";
        if ("25".equals(maskBit)) return "255.255.255.128";
        if ("26".equals(maskBit)) return "255.255.255.192";
        if ("27".equals(maskBit)) return "255.255.255.224";
        if ("28".equals(maskBit)) return "255.255.255.240";
        if ("29".equals(maskBit)) return "255.255.255.248";
        if ("30".equals(maskBit)) return "255.255.255.252";
        if ("31".equals(maskBit)) return "255.255.255.254";
        if ("32".equals(maskBit)) return "255.255.255.255";
        return "-1";
    }

    public static double ipToDouble(String ip) {
        String[] arr = ip.split("\\.");
        double d1 = Double.parseDouble(arr[0]);
        double d2 = Double.parseDouble(arr[1]);
        double d3 = Double.parseDouble(arr[2]);
        double d4 = Double.parseDouble(arr[3]);
        return d1 * Math.pow(256, 3) + d2 * Math.pow(256, 2) + d3 * 256 + d4;
    }



    /**
     * 根据 ip 掩码 计算IP段的终止IP 如 IP串 218.240.38.69 255.255.255.0
     *
     * @param ip
     *            给定的IP，如218.240.38.69
     * @param mask
     *            给定的掩码，如255.255.255.0
     * @return 终止IP的字符串表示
     */
    public static String getEndIpStr2(String ip, String mask)
    {
        return getIpFromLong((getIpFromString(ip) & getIpFromString(mask)) + ~getIpFromString(mask));
    }
}