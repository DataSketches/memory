/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.datasketches.memory.test;

import static org.testng.Assert.assertEquals;

import org.apache.datasketches.memory.WritableMemory;
import org.testng.annotations.Test;

/**
 * @author Lee Rhodes
 */
@SuppressWarnings("javadoc")
public class XxHash64LoopingTest {

  /*
   * This test is adapted from
   * <a href="https://github.com/OpenHFT/Zero-Allocation-Hashing/blob/master/src/test/java/net/openhft/hashing/XxHashTest.java">
   * OpenHFT/Zero-Allocation-Hashing</a> to test hash compatibility with that implementation.
   * See LICENSE.
   */
  @Test
  public void testWithSeed() {
    long seed = 42L;
    for (int i = 0; i < 1025; i++) {
      byte[] byteArr = new byte[i];
      for (int j = 0; j < byteArr.length; j++) { byteArr[j] = (byte) j; }
      WritableMemory wmem = WritableMemory.writableWrap(byteArr);
      long hash = wmem.xxHash64(0, byteArr.length, seed);
      assertEquals(hash, HASHES_OF_LOOPING_BYTES_WITH_SEED_42[i]);
    }
  }

  /*This data is from
   * <a href="https://github.com/OpenHFT/Zero-Allocation-Hashing/blob/master/src/test/java/net/openhft/hashing/XxHashTest.java">
   * OpenHFT/Zero-Allocation-Hashing</a> to test hash compatibility with that implementation.
   * See LICENSE.
   */
  private static final long[] HASHES_OF_LOOPING_BYTES_WITH_SEED_42 = {
    -7444071767201028348L,
    -8959994473701255385L,
    7116559933691734543L,
    6019482000716350659L,
    -6625277557348586272L,
    -5507563483608914162L,
    1540412690865189709L,
    4522324563441226749L,
    -7143238906056518746L,
    -7989831429045113014L,
    -7103973673268129917L,
    -2319060423616348937L,
    -7576144055863289344L,
    -8903544572546912743L,
    6376815151655939880L,
    5913754614426879871L,
    6466567997237536608L,
    -869838547529805462L,
    -2416009472486582019L,
    -3059673981515537339L,
    4211239092494362041L,
    1414635639471257331L,
    166863084165354636L,
    -3761330575439628223L,
    3524931906845391329L,
    6070229753198168844L,
    -3740381894759773016L,
    -1268276809699008557L,
    1518581707938531581L,
    7988048690914090770L,
    -4510281763783422346L,
    -8988936099728967847L,
    -8644129751861931918L,
    2046936095001747419L,
    339737284852751748L,
    -8493525091666023417L,
    -3962890767051635164L,
    -5799948707353228709L,
    -6503577434416464161L,
    7718729912902936653L,
    191197390694726650L,
    -2677870679247057207L,
    20411540801847004L,
    2738354376741059902L,
    -3754251900675510347L,
    -3208495075154651980L,
    5505877218642938179L,
    6710910171520780908L,
    -9060809096139575515L,
    6936438027860748388L,
    -6675099569841255629L,
    -5358120966884144380L,
    -4970515091611332076L,
    -1810965683604454696L,
    -516197887510505242L,
    1240864593087756274L,
    6033499571835033332L,
    7223146028771530185L,
    909128106589125206L,
    1567720774747329341L,
    -1867353301780159863L,
    4655107429511759333L,
    5356891185236995950L,
    182631115370802890L,
    -3582744155969569138L,
    595148673029792797L,
    495183136068540256L,
    5536689004903505647L,
    -8472683670935785889L,
    -4335021702965928166L,
    7306662983232020244L,
    4285260837125010956L,
    8288813008819191181L,
    -3442351913745287612L,
    4883297703151707194L,
    9135546183059994964L,
    123663780425483012L,
    509606241253238381L,
    5940344208569311369L,
    -2650142344608291176L,
    3232776678942440459L,
    -922581627593772181L,
    7617977317085633049L,
    7154902266379028518L,
    -5806388675416795571L,
    4368003766009575737L,
    -2922716024457242064L,
    4771160713173250118L,
    3275897444752647349L,
    -297220751499763878L,
    5095659287766176401L,
    1181843887132908826L,
    9058283605301070357L,
    3984713963471276643L,
    6050484112980480005L,
    1551535065359244224L,
    565337293533335618L,
    7412521035272884309L,
    -4735469481351389369L,
    6998597101178745656L,
    -9107075101236275961L,
    5879828914430779796L,
    6034964979406620806L,
    5666406915264701514L,
    -4666218379625258428L,
    2749972203764815656L,
    -782986256139071446L,
    6830581400521008570L,
    2588852022632995043L,
    -5484725487363818922L,
    -3319556935687817112L,
    6481961252981840893L,
    2204492445852963006L,
    -5301091763401031066L,
    -2615065677047206256L,
    -6769817545131782460L,
    -8421640685322953142L,
    -3669062629317949176L,
    -9167016978640750490L,
    2783671191687959562L,
    -7599469568522039782L,
    -7589134103255480011L,
    -5932706841188717592L,
    -8689756354284562694L,
    -3934347391198581249L,
    -1344748563236040701L,
    2172701592984478834L,
    -5322052340624064417L,
    -8493945390573620511L,
    3349021988137788403L,
    -1806262525300459538L,
    -8091524448239736618L,
    4022306289903960690L,
    -8346915997379834224L,
    -2106001381993805461L,
    -5784123934724688161L,
    6775158099649720388L,
    -3869682756870293568L,
    4356490186652082006L,
    8469371446702290916L,
    -2972961082318458602L,
    -7188106622222784561L,
    -4961006366631572412L,
    3199991182014172900L,
    2917435868590434179L,
    8385845305547872127L,
    7706824402560674655L,
    -1587379863634865277L,
    -4212156212298809650L,
    -1305209322000720233L,
    -7866728337506665880L,
    8195089740529247049L,
    -4876930125798534239L,
    798222697981617129L,
    -2441020897729372845L,
    -3926158482651178666L,
    -1254795122048514130L,
    5192463866522217407L,
    -5426289318796042964L,
    -3267454004443530826L,
    471043133625225785L,
    -660956397365869974L,
    -6149209189144999161L,
    -2630977660039166559L,
    8512219789663151219L,
    -3309844068134074620L,
    -6211275327487847132L,
    -2130171729366885995L,
    6569302074205462321L,
    4855778342281619706L,
    3867211421508653033L,
    -3002480002418725542L,
    -8297543107467502696L,
    8049642289208775831L,
    -5439825716055425635L,
    7251760070798756432L,
    -4774526021749797528L,
    -3892389575184442548L,
    5162451061244344424L,
    6000530226398686578L,
    -5713092252241819676L,
    8740913206879606081L,
    -8693282419677309723L,
    1576205127972543824L,
    5760354502610401246L,
    3173225529903529385L,
    1785166236732849743L,
    -1024443476832068882L,
    -7389053248306187459L,
    1171021620017782166L,
    1471572212217428724L,
    7720766400407679932L,
    -8844781213239282804L,
    -7030159830170200877L,
    2195066352895261150L,
    1343620937208608634L,
    9178233160016731645L,
    -757883447602665223L,
    3303032934975960867L,
    -3685775162104101116L,
    -4454903657585596656L,
    -5721532367620482629L,
    8453227136542829644L,
    5397498317904798888L,
    7820279586106842836L,
    -2369852356421022546L,
    3910437403657116169L,
    6072677490463894877L,
    -2651044781586183960L,
    5173762670440434510L,
    -2970017317595590978L,
    -1024698859439768763L,
    -3098335260967738522L,
    -1983156467650050768L,
    -8132353894276010246L,
    -1088647368768943835L,
    -3942884234250555927L,
    7169967005748210436L,
    2870913702735953746L,
    -2207022373847083021L,
    1104181306093040609L,
    5026420573696578749L,
    -5874879996794598513L,
    -4777071762424874671L,
    -7506667858329720470L,
    -2926679936584725232L,
    -5530649174168373609L,
    5282408526788020384L,
    3589529249264153135L,
    -6220724706210580398L,
    -7141769650716479812L,
    5142537361821482047L,
    -7029808662366864423L,
    -6593520217660744466L,
    1454581737122410695L,
    -139542971769349865L,
    1727752089112067235L,
    -775001449688420017L,
    -5011311035350652032L,
    -8671171179275033159L,
    -2850915129917664667L,
    -5258897903906998781L,
    -6954153088230718761L,
    -4070351752166223959L,
    -6902592976462171099L,
    -7850366369290661391L,
    -4562443925864904705L,
    3186922928616271015L,
    2208521081203400591L,
    -2727824999830592777L,
    -3817861137262331295L,
    2236720618756809066L,
    -4888946967413746075L,
    -446884183491477687L,
    -43021963625359034L,
    -5857689226703189898L,
    -2156533592262354883L,
    -2027655907961967077L,
    7151844076490292500L,
    -5029149124756905464L,
    526404452686156976L,
    8741076980297445408L,
    7962851518384256467L,
    -105985852299572102L,
    -2614605270539434398L,
    -8265006689379110448L,
    8158561071761524496L,
    -6923530157382047308L,
    5551949335037580397L,
    565709346370307061L,
    -4780869469938333359L,
    6931895917517004830L,
    565234767538051407L,
    -8663136372880869656L,
    1427340323685448983L,
    6492705666640232290L,
    1481585578088475369L,
    -1712711110946325531L,
    3281685342714380741L,
    6441384790483098576L,
    -1073539554682358394L,
    5704050067194788964L,
    -5495724689443043319L,
    -5425043165837577535L,
    8349736730194941321L,
    -4123620508872850061L,
    4687874980541143573L,
    -468891940172550975L,
    -3212254545038049829L,
    -6830802881920725628L,
    9033050533972480988L,
    4204031879107709260L,
    -677513987701096310L,
    -3286978557209370155L,
    1644111582609113135L,
    2040089403280131741L,
    3323690950628902653L,
    -7686964480987925756L,
    -4664519769497402737L,
    3358384147145476542L,
    -4699919744264452277L,
    -4795197464927839170L,
    5051607253379734527L,
    -8987703459734976898L,
    8993686795574431834L,
    -2688919474688811047L,
    375938183536293311L,
    1049459889197081920L,
    -1213022037395838295L,
    4932989235110984138L,
    -6647247877090282452L,
    -7698817539128166242L,
    -3264029336002462659L,
    6487828018122309795L,
    -2660821091484592878L,
    7104391069028909121L,
    -1765840012354703384L,
    85428166783788931L,
    -6732726318028261938L,
    7566202549055682933L,
    229664898114413280L,
    -1474237851782211353L,
    -1571058880058007603L,
    -7926453582850712144L,
    2487148368914275243L,
    8740031015380673473L,
    1908345726881363169L,
    -2510061320536523178L,
    7854780026906019630L,
    -6023415596650016493L,
    -6264841978089051107L,
    4024998278016087488L,
    -4266288992025826072L,
    -3222176619422665563L,
    -1999258726038299316L,
    1715270077442385636L,
    6764658837948099754L,
    -8646962299105812577L,
    -51484064212171546L,
    -1482515279051057493L,
    -8663965522608868414L,
    -256555202123523670L,
    1973279596140303801L,
    -7280796173024508575L,
    -5691760367231354704L,
    -5915786562256300861L,
    -3697715074906156565L,
    3710290115318541949L,
    6796151623958134374L,
    -935299482515386356L,
    -7078378973978660385L,
    5379481350768846927L,
    -9011221735308556302L,
    5936568631579608418L,
    -6060732654964511813L,
    -4243141607840017809L,
    3198488845875349355L,
    -7809288876010447646L,
    4371587872421472389L,
    -1304197371105522943L,
    7389861473143460103L,
    -1892352887992004024L,
    2214828764044713398L,
    6347546952883613388L,
    1275694314105480954L,
    -5262663163358903733L,
    1524757505892047607L,
    1474285098416162746L,
    -7976447341881911786L,
    4014100291977623265L,
    8994982266451461043L,
    -7737118961020539453L,
    -2303955536994331092L,
    1383016539349937136L,
    1771516393548245271L,
    -5441914919967503849L,
    5449813464890411403L,
    -3321280356474552496L,
    4084073849712624363L,
    4290039323210935932L,
    2449523715173349652L,
    7494827882138362156L,
    9035007221503623051L,
    5722056230130603177L,
    -5443061851556843748L,
    -7554957764207092109L,
    447883090204372074L,
    533916651576859197L,
    -3104765246501904165L,
    -4002281505194601516L,
    -8402008431255610992L,
    -408273018037005304L,
    214196458752109430L,
    6458513309998070914L,
    2665048360156607904L,
    96698248584467992L,
    -3238403026096269033L,
    6759639479763272920L,
    -4231971627796170796L,
    -2149574977639731179L,
    -1437035755788460036L,
    -6000005629185669767L,
    145244292800946348L,
    -3056352941404947199L,
    3748284277779018970L,
    7328354565489106580L,
    -2176895260373660284L,
    3077983936372755601L,
    1215485830019410079L,
    683050801367331140L,
    -3173237622987755212L,
    -1951990779107873701L,
    -4714366021269652421L,
    4934690664256059008L,
    1674823104333774474L,
    -3974408282362828040L,
    2001478896492417760L,
    -4115105568354384199L,
    -2039694725495941666L,
    -587763432329933431L,
    -391276713546911316L,
    -5543400904809469053L,
    1882564440421402418L,
    -4991793588968693036L,
    3454088185914578321L,
    2290855447126188424L,
    3027910585026909453L,
    2136873580213167431L,
    -6243562989966916730L,
    5887939953208193029L,
    -3491821629467655741L,
    -3138303216306660662L,
    8572629205737718669L,
    4154439973110146459L,
    5542921963475106759L,
    -2025215496720103521L,
    -4047933760493641640L,
    -169455456138383823L,
    -1164572689128024473L,
    -8551078127234162906L,
    -7247713218016599028L,
    8725299775220778242L,
    6263466461599623132L,
    7931568057263751768L,
    7365493014712655238L,
    -7343740914722477108L,
    8294118602089088477L,
    7677867223984211483L,
    -7052188421655969232L,
    -3739992520633991431L,
    772835781531324307L,
    881441588914692737L,
    6321450879891466401L,
    5682516032668315027L,
    8493068269270840662L,
    -3895212467022280567L,
    -3241911302335746277L,
    -7199586338775635848L,
    -4606922569968527974L,
    -806850906331637768L,
    2433670352784844513L,
    -5787982146811444512L,
    7852193425348711165L,
    8669396209073850051L,
    -6898875695148963118L,
    6523939610287206782L,
    -8084962379210153174L,
    8159432443823995836L,
    -2631068535470883494L,
    -338649779993793113L,
    6514650029997052016L,
    3926259678521802094L,
    5443275905907218528L,
    7312187582713433551L,
    -2993773587362997676L,
    -1068335949405953411L,
    4499730398606216151L,
    8538015793827433712L,
    -4057209365270423575L,
    -1504284818438273559L,
    -6460688570035010846L,
    1765077117408991117L,
    8278320303525164177L,
    8510128922449361533L,
    1305722765578569816L,
    7250861238779078656L,
    -576624504295396147L,
    -4363714566147521011L,
    -5932111494795524073L,
    1837387625936544674L,
    -4186755953373944712L,
    -7657073597826358867L,
    140408487263951108L,
    5578463635002659628L,
    3400326044813475885L,
    -6092804808386714986L,
    -2410324417287268694L,
    3222007930183458970L,
    4932471983280850419L,
    3554114546976144528L,
    -7216067928362857082L,
    -6115289896923351748L,
    -6769646077108881947L,
    4263895947722578066L,
    2939136721007694271L,
    1426030606447416658L,
    -1316192446807442076L,
    5366182640480055129L,
    6527003877470258527L,
    5849680119000207603L,
    5263993237214222328L,
    -6936533648789185663L,
    -9063642143790846605L,
    3795892210758087672L,
    4987213125282940176L,
    2505500970421590750L,
    -1014022559552365387L,
    -3574736245968367770L,
    1180676507127340259L,
    -2261908445207512503L,
    -8416682633172243509L,
    1114990703652673283L,
    7753746660364401380L,
    1874908722469707905L,
    2033421444403047677L,
    21412168602505589L,
    385957952615286205L,
    2053171460074727107L,
    1915131899400103774L,
    6680879515029368390L,
    568807208929724162L,
    -6211541450459087674L,
    -5026690733412145448L,
    1384781941404886235L,
    -98027820852587266L,
    1806580495924249669L,
    6322077317403503963L,
    9078162931419569939L,
    -2809061215428363978L,
    7697867577577415733L,
    -5270063855897737274L,
    5649864555290587388L,
    -6970990547695444247L,
    579684606137331754L,
    3871931565451195154L,
    2030008578322050218L,
    -5012357307111799829L,
    -2271365921756144065L,
    4551962665158074190L,
    -3385474923040271312L,
    -7647625164191633577L,
    6634635380316963029L,
    -5201190933687061585L,
    8864818738548593973L,
    2855828214210882907L,
    9154512990734024165L,
    -6945306719789457786L,
    1200243352799481087L,
    875998327415853787L,
    1275313054449881011L,
    -6105772045375948736L,
    -2926927684328291437L,
    9200050852144954779L,
    5188726645765880663L,
    5197037323312705176L,
    3434926231010121611L,
    -5054013669361906544L,
    2582959199749224670L,
    -6053757512723474059L,
    -5016308176846054473L,
    -2509827316698626133L,
    7700343644503853204L,
    -1997627249894596731L,
    3993168688325352290L,
    -8181743677541277704L,
    3719056119682565597L,
    -7264411659282947790L,
    7177028972346484464L,
    -5460831176884283278L,
    1799904662416293978L,
    -6549616005092764514L,
    5472403994001122052L,
    8683463751708388502L,
    -7873363037838316398L,
    689134758256487260L,
    -1287443614028696450L,
    4452712919702709507L,
    762909374167538893L,
    6594302592326281411L,
    1183786629674781984L,
    5021847859620133476L,
    -2490098069181538915L,
    5105145136026716679L,
    4437836948098585718L,
    1987270426215858862L,
    6170312798826946249L,
    634297557126003407L,
    -1672811625495999581L,
    6282971595586218191L,
    4549149305727581687L,
    -5652165370435317782L,
    1064501550023753890L,
    -5334885527127139723L,
    -6904378001629481237L,
    -1807576691784201230L,
    -205688432992053911L,
    7621619053293393289L,
    6258649161313982470L,
    -1111634238359342096L,
    -8044260779481691987L,
    400270655839010807L,
    -7806833581382890725L,
    -2970563349459508036L,
    -7392591524816802798L,
    2918924613160219805L,
    -6444161627929149002L,
    6096497501321778876L,
    -1477975665655830038L,
    1690651307597306138L,
    -2364076888826085362L,
    -6521987420014905821L,
    -4419193480146960582L,
    3538587780233092477L,
    8374665961716940404L,
    7492412312405424500L,
    6311662249091276767L,
    -1240235198282023566L,
    5478559631401166447L,
    3476714419313462133L,
    377427285984503784L,
    2570472638778991109L,
    -2741381313777447835L,
    -7123472905503039596L,
    2493658686946955193L,
    1024677789035847585L,
    -2916713904339582981L,
    -4532003852004642304L,
    -2202143560366234111L,
    5832267856442755135L,
    -261740607772957384L,
    239435959690278014L,
    5755548341947719409L,
    6138795458221887696L,
    -7709506987360146385L,
    -6657487758065140444L,
    -7006376793203657499L,
    6544409861846502033L,
    3171929352014159247L,
    1051041925048792869L,
    2617300158375649749L,
    952652799620095175L,
    -576661730162168147L,
    -1634191369221345988L,
    4833656816115993519L,
    647566759700005786L,
    2473810683785291822L,
    3005977181064745326L,
    -3321881966853149523L,
    7595337666427588699L,
    6004093624251057224L,
    -563917505657690279L,
    6117428527147449302L,
    -6287297509522976113L,
    -4527219334756214406L,
    742626429298092489L,
    3057351806086972041L,
    645967551210272605L,
    -4428701157828864227L,
    3236379103879435414L,
    -8477089892132066300L,
    -6127365537275859058L,
    -4052490484706946358L,
    -8004854976625046469L,
    -3679456917426613424L,
    -8212793762082595299L,
    -818288739465424130L,
    1358812099481667095L,
    7835987612195254310L,
    -3663247409614323059L,
    -2931105150130396604L,
    7296136776835614792L,
    -2014557408985889628L,
    7267662411237959788L,
    3699280615819277743L,
    -212010675469091396L,
    -6518374332458360120L,
    145026010541628849L,
    1879297324213501001L,
    -7146296067751816833L,
    -5002958800391379931L,
    6060682439924517608L,
    -432234782921170964L,
    -6669688947353256956L,
    7728943532792041267L,
    830911367341171721L,
    3396934884314289432L,
    -779464156662780749L,
    2330041851883352285L,
    -4783350380736276693L,
    -5758476056890049254L,
    -7551552301614791791L,
    1253334187723911710L,
    -2685018208308798978L,
    5379636036360946454L,
    6154668487114681217L,
    -8641287462255458898L,
    4676087643800649558L,
    -2405142641398691475L,
    1088685126864246881L,
    6431149082338374041L,
    -607357695335069155L,
    -720970692129524140L,
    2648766932394044468L,
    8408344790179354573L,
    -6193808387735667350L,
    7722524628524697419L,
    -6975433852560238120L,
    -2925851029234475295L,
    -4274458387165211028L,
    -8355836377702147319L,
    5278146397877332061L,
    8502098812383680707L,
    2292836642336580326L,
    -6127608082651070062L,
    2222301962240611208L,
    -1930887695854799378L,
    7640503480494894592L,
    1162652186586436094L,
    -1918002592943761683L,
    7648998601717261840L,
    -8472603250832757057L,
    -988877663117552456L,
    2368458128168026494L,
    -6480813811998475245L,
    -5896967824416018967L,
    -2593783161701820446L,
    6950098417530252598L,
    6362589545555771236L,
    7981389665448567125L,
    3954017080198558850L,
    1626078615050230622L,
    6650159066527969109L,
    697345338922935394L,
    -1226816215461768626L,
    8740408765973837440L,
    -4194155864629568323L,
    7016680023232424746L,
    6043281358142429469L,
    -4201005667174376809L,
    1216727117859013155L,
    6367202436544203935L,
    35414869396444636L,
    3715622794033998412L,
    488654435687670554L,
    -2503747297224687460L,
    3147101919441470388L,
    -8248611218693190922L,
    970697264481229955L,
    3411465763826851418L,
    9117405004661599969L,
    -5204346498331519734L,
    -19637460819385174L,
    -5039124225167977219L,
    2990108874601696668L,
    -2623857460235459202L,
    4256291692861397446L,
    6724147860870760443L,
    3558616688507246537L,
    6487680097936412800L,
    -6470792832935928161L,
    4314814550912237614L,
    -1292878983006062345L,
    6791915152630414174L,
    5971652079925815310L,
    2557529546662864312L,
    466175054322801580L,
    -585216717310746872L,
    -2486640422147349036L,
    7212029603994220134L,
    3958995069888972500L,
    4950471855791412790L,
    -3721948842035712763L,
    -6184503487488243051L,
    4079570444585775332L,
    -3952156172546996872L,
    4543894231118208322L,
    -1739995588466209963L,
    9155948355455935530L,
    5821980345462207860L,
    -2431287667309520417L,
    -3890108130519441316L,
    -558124689277030490L,
    6079823537335801717L,
    5409742395192364262L,
    -2329885777717160453L,
    -7332804342513677651L,
    1466490574975950555L,
    -420549419907427929L,
    -5249909814389692516L,
    -5145692168206210661L,
    5934113980649113921L,
    3241618428555359661L,
    -6622110266160980250L,
    5048250878669516223L,
    5747219637359976174L,
    2975906212588223728L,
    5730216838646273215L,
    -176713127129024690L,
    6734624279336671146L,
    5127866734316017180L,
    7111761230887705595L,
    3457811808274317235L,
    3362961434604932375L,
    -1877869936854991246L,
    7171428594877765665L,
    -8252167178400462374L,
    -6306888185035821047L,
    -6684702191247683887L,
    -7754928454824190529L,
    -1902605599135704386L,
    -4037319846689421239L,
    8493746058123583457L,
    -8156648963857047193L,
    2051510355149839497L,
    -1256416624177218909L,
    -3344927996254072010L,
    -1838853051925943568L,
    316927471680974556L,
    -1502257066700798003L,
    -5836095610125837606L,
    -1594125583615895424L,
    1442211486559637962L,
    -144295071206619569L,
    5159850900959273410L,
    4589139881166423678L,
    -7038726987463097509L,
    2886082400772974595L,
    2780759114707171916L,
    5694649587906297495L,
    1260349041268169667L,
    4921517488271434890L,
    644696475796073018L,
    6262811963753436289L,
    -6128198676595868773L,
    -3625352083004760261L,
    -8751453332943236675L,
    8749249479868749221L,
    -2450808199545048250L,
    -6517435817046180917L,
    -3433321727429234998L,
    -2591586258908763451L,
    3847750870868804507L,
    6603614438546398643L,
    -7598682191291031287L,
    8710261565627204971L,
    4753389483755344355L,
    -4645333069458786881L,
    -6742695046613492214L,
    643070478568866643L,
    -7543096104151965610L,
    7171495384655926161L,
    595063872610714431L,
    3292310150781130424L,
    4326847806055440904L,
    -4580020566072794152L,
    3142286571820373678L,
    5530356537440155930L,
    546372639737516181L,
    7401214477400367500L,
    7406531960402873109L,
    3287639667219172570L,
    4977301681213633671L,
    5253257820925174498L,
    2906216636104297878L,
    6142955758238347523L,
    -3498651268741727235L,
    -5875053958265588593L,
    3896719087169993883L,
    -910904726885775073L,
    380107493197368177L,
    -4993591912695447004L,
    2970487257212582761L,
    2551762717569548774L,
    953061649962736812L,
    8949739538606589463L,
    -2962839167079475801L,
    -1375673191272573835L,
    3761793818361866390L,
    -389577789190726878L,
    5661262051502180269L,
    -6558556411143987683L,
    -702798336372315031L,
    -336662820551371779L,
    998576401126580155L,
    -5945021269112582755L,
    6108533925730179871L,
    2207095297001999618L,
    -9042779159998880435L,
    -6177868444342118372L,
    6775965402605895077L,
    -3788428885163306576L,
    7790055010527190387L,
    3581587652196995358L,
    -6176354155561607694L,
    -5859381340906321207L,
    395898765763528395L,
    8132967590863909348L,
    -3329092504090544483L,
    -6785855381158040247L,
    1497218517051796750L,
    -5352392845588925911L,
    -6271364901230559194L,
    2314830370653350118L,
    -7617588269001325450L,
    1423166885758213795L,
    8538612578307869519L,
    -61918791718295474L,
    -8177103503192338593L,
    -4740086042584326695L,
    3677931948215558698L,
    6558856291580149558L,
    2674975452453336335L,
    5133796555646930522L,
    5139252693299337100L,
    7949476871295347205L,
    4407815324662880678L,
    -3758305875280581215L,
    6066309507576587415L,
    -7368508486398350973L,
    -3181640264332856492L,
    6905100869343314145L,
    3677177673848733417L,
    8862933624870506941L,
    -8575223195813810568L,
    9178470351355678144L,
    4677809017145408358L,
    -1194833416287894989L,
    3436364743255571183L,
    -5204770725795363579L,
    560599448536335263L,
    -3192077522964776200L,
    -751575299648803575L,
    6334581746534596579L,
    -8358187891202563300L,
    -1462480609823525055L,
    5605961062646987941L,
    4968399805931440889L,
    7968693270782626653L,
    -5868205923557518188L,
    1830234928743560617L,
    -8435261076693154407L,
    2138416970728681332L,
    8088740745199685138L,
    806532400344230520L,
    1800590379902909333L,
    -8909128842071238901L,
    -7357495566969170860L,
    3679766664126940553L,
    2060050474865839094L,
    2363972840121763414L,
    525695004292982714L,
    -1224842191746529593L,
    7011317848855545003L,
    -6337167558180299938L,
    -5184688833363785939L,
    -8426673387248359061L,
    -5035438815930785229L,
    3521810320608058994L,
    4803742557254962242L,
    6623527039545786598L,
    -1221475882122634738L,
    -3344794405518401087L,
    6510298498414053658L,
    2844753907937720338L,
    90502309714994895L,
    -750403235344282494L,
    -4825474181021465833L,
    -3405519947983849510L,
    3503875590944089793L,
    7286294700691822468L,
    7828126881500292486L,
    8437899353709338096L,
    136052254470293480L,
    1113259077339995086L,
    -8244887265606191121L,
    8089569503800461649L,
    -1429698194850157567L,
    1575595674002364989L,
    3576095286627428675L,
    -7653655285807569222L,
    -6053506977362539111L,
    -3923855345805787169L,
    -8001149080454232377L,
    -4382867706931832271L,
    4212860258835896297L,
    4207674254247034014L,
    5519424058779519159L,
    -754483042161434654L,
    1434113479814210082L,
    -6416645032698336896L,
    5624329676066514819L,
    -8229557208322175959L,
    3922640911653270376L,
    7826932478782081910L,
    -4862787164488635842L,
    1449234668827944573L,
    -1781657689570106327L,
    5442827552725289699L,
    3589862161007644641L,
    4787115581650652778L,
    -3512152721942525726L,
    -6750103117958685206L,
    5012970446659949261L,
    6797752795961689017L,
    5086454597639943700L,
    -7616068364979994076L,
    1492846825433110217L,
    2967476304433704510L,
    -8413824338284112078L,
    -1319049442043273974L,
    -1756090916806844109L,
    -9061091728950139525L,
    -6864767830358160810L,
    4879532090226251157L,
    5528644708740739488L
  };
}