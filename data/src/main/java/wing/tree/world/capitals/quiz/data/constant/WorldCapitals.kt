package wing.tree.world.capitals.quiz.data.constant

import wing.tree.world.capitals.quiz.data.R
import wing.tree.world.capitals.quiz.data.model.Capital
import wing.tree.world.capitals.quiz.data.model.Continent
import wing.tree.world.capitals.quiz.data.model.Nation
import wing.tree.world.capitals.quiz.data.model.Role

sealed interface WorldCapitals : Comparable<WorldCapitals> {
    val continent: Continent
    val count: Int get() = nations.count()
    val nations: List<Nation>
    val ordinal: Int

    override fun compareTo(other: WorldCapitals): Int {
        return ordinal.compareTo(other.ordinal)
    }

    fun capitals(vararg capitals: Capital): List<Capital> = listOf(*capitals)

    object America : WorldCapitals {
        override val continent: Continent = Continent.AMERICA
        override val nations: List<Nation> = listOf(
            Nation(capitals = capitals(Capital(capital = R.string.capital_georgetown)), country = R.string.country_guyana, key = "가이아나"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_guatemala_city)), country = R.string.country_guatemala, key = "과테말라"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_saint_georges)), country = R.string.country_grenada, key = "그레나다"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_nuuk)), country = R.string.country_greenland, key = "그린란드"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_managua)), country = R.string.country_nicaragua, key = "니카라과"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_roseau)), country = R.string.country_dominica, key = "도미니카"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_santo_domingo)), country = R.string.country_dominican_republic, key = "도미니카 공화국"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_mexico_city)), country = R.string.country_mexico, key = "멕시코"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_washington_dc)), country = R.string.country_united_states, key = "미국"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_bridgetown)), country = R.string.country_barbados, key = "바베이도스"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_nassau)), country = R.string.country_bahamas, key = "바하마"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_caracas)), country = R.string.country_venezuela, key = "베네수엘라"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_belmopan)), country = R.string.country_belize, key = "벨리즈"),
            Nation(
                capitals = capitals(
                    Capital(capital = R.string.capital_la_paz, role = Role.ADMINISTRATIVE),
                    Capital(capital = R.string.capital_sucre, role = Role.JUDICIAL),
                ),
                country = R.string.country_bolivia,
                key = "볼리비아",
            ),
            Nation(capitals = capitals(Capital(capital = R.string.capital_brasilia)), country = R.string.country_brazil, key = "브라질"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_castries)), country = R.string.country_saint_lucia, key = "세인트루시아"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_kingstown)), country = R.string.country_saint_vincent_grenadines, key = "세인트빈센트 그레나딘"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_basseterre)), country = R.string.country_saint_kitts_nevis, key = "세인트키츠 네비스"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_paramaribo)), country = R.string.country_suriname, key = "수리남"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_buenos_aires)), country = R.string.country_argentina, key = "아르헨티나"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_port_au_prince)), country = R.string.country_haiti, key = "아이티"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_saint_johns)), country = R.string.country_antigua_barbuda, key = "앤티가 바부다"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_quito)), country = R.string.country_ecuador, key = "에콰도르"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_san_salvador)), country = R.string.country_el_salvador, key = "엘살바도르"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_tegucigalpa)), country = R.string.country_honduras, key = "온두라스"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_montevideo)), country = R.string.country_uruguay, key = "우루과이"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_kingston)), country = R.string.country_jamaica, key = "자메이카"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_santiago)), country = R.string.country_chile, key = "칠레"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_ottawa)), country = R.string.country_canada, key = "캐나다"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_san_jose)), country = R.string.country_costa_rica, key = "코스타리카"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_bogota)), country = R.string.country_colombia, key = "콜롬비아"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_havana)), country = R.string.country_cuba, key = "쿠바"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_port_of_spain)), country = R.string.country_trinidad_tobago, key = "트리니다드 토바고"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_panama_city)), country = R.string.country_panama, key = "파나마"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_asuncion)), country = R.string.country_paraguay, key = "파라과이"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_lima)), country = R.string.country_peru, key = "페루"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_san_juan)), country = R.string.country_puerto_rico, key = "푸에르토리코"),
        )

        override val ordinal: Int = ZERO
    }

    object Asia : WorldCapitals {
        override val continent: Continent = Continent.ASIA
        override val nations: List<Nation> = listOf(
            Nation(capitals = capitals(Capital(capital = R.string.capital_conakry)), country = R.string.country_guinea, key = "기니"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_kathmandu)), country = R.string.country_nepal, key = "네팔"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_seoul)), country = R.string.country_south_korea, key = "대한민국"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_dili)), country = R.string.country_east_timor, key = "동티모르"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_vientiane)), country = R.string.country_laos, key = "라오스"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_beirut)), country = R.string.country_lebanon, key = "레바논"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_macau)), country = R.string.country_macau, key = "마카오"),
            Nation(
                capitals = capitals(
                    Capital(capital = R.string.capital_kuala_lumpur, role = Role.OFFICIAL),
                    Capital(capital = R.string.capital_putrajaya, role = Role.ADMINISTRATIVE)
                ),
                country = R.string.country_malaysia,
                key = "말레이시아",
            ),
            Nation(capitals = capitals(Capital(capital = R.string.capital_male)), country = R.string.country_maldives, key = "몰디브"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_ulaanbaatar)), country = R.string.country_mongolia, key = "몽골"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_naypyidaw)), country = R.string.country_myanmar, key = "미얀마"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_manama)), country = R.string.country_bahrain, key = "바레인"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_dhaka)), country = R.string.country_bangladesh, key = "방글라데시"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_hanoi)), country = R.string.country_vietnam, key = "베트남"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_thimphu)), country = R.string.country_bhutan, key = "부탄"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_bandar_seri_begawan)), country = R.string.country_brunei, key = "브루나이"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_riyadh)), country = R.string.country_saudi_arabia, key = "사우디아라비아"),
            Nation(
                capitals = capitals(
                    Capital(capital = R.string.capital_colombo, role = Role.COMMERCIAL),
                    Capital(capital = R.string.capital_sri_jayawardenepura_kotte, role = Role.ADMINISTRATIVE),
                ),
                country = R.string.country_sri_lanka,
                key = "스리랑카",
            ),
            Nation(capitals = capitals(Capital(capital = R.string.capital_damascus)), country = R.string.country_syria, key = "시리아"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_singapore)), country = R.string.country_singapore, key = "싱가포르"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_abu_dhabi)), country = R.string.country_united_arab_emirates, key = "아랍에미리트"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_yerevan)), country = R.string.country_armenia, key = "아르메니아"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_stepanakert)), country = R.string.country_artsakh, key = "아르차흐공화국"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_baku)), country = R.string.country_azerbaijan, key = "아제르바이잔"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_kabul)), country = R.string.country_afghanistan, key = "아프가니스탄"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_sanaa)), country = R.string.country_yemen, key = "예멘"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_muscat)), country = R.string.country_oman, key = "오만"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_amman)), country = R.string.country_jordan, key = "요르단"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_tashkent)), country = R.string.country_uzbekistan, key = "우즈베키스탄"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_baghdad)), country = R.string.country_iraq, key = "이라크"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_tehran)), country = R.string.country_iran, key = "이란"),
            Nation(
                capitals = capitals(
                    Capital(capital = R.string.capital_tel_aviv, role = Role.DE_FACTO),
                    Capital(capital = R.string.capital_jerusalem, role = Role.CLAIMED),
                ),
                country = R.string.country_israel,
                key = "이스라엘",
            ),
            Nation(capitals = capitals(Capital(capital = R.string.capital_new_delhi)), country = R.string.country_india, key = "인도"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_jakarta)), country = R.string.country_indonesia, key = "인도네시아"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_tokyo)), country = R.string.country_japan, key = "일본"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_taipei)), country = R.string.country_taiwan, key = "대만"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_beijing)), country = R.string.country_china, key = "중국"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_astana)), country = R.string.country_kazakhstan, key = "카자흐스탄"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_doha)), country = R.string.country_qatar, key = "카타르"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_phnom_penh)), country = R.string.country_cambodia, key = "캄보디아"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_kuwait_city)), country = R.string.country_kuwait, key = "쿠웨이트"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_bishkek)), country = R.string.country_kyrgyzstan, key = "키르기스스탄"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_dushanbe)), country = R.string.country_tajikistan, key = "타지키스탄"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_bangkok)), country = R.string.country_thailand, key = "태국"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_ashgabat)), country = R.string.country_turkmenistan, key = "투르크메니스탄"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_islamabad)), country = R.string.country_pakistan, key = "파키스탄"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_ramallah)), country = R.string.country_palestine, key = "팔레스타인"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_manila)), country = R.string.country_philippines, key = "필리핀"),
        )

        override val ordinal: Int = ONE
    }

    object Europe : WorldCapitals {
        override val continent: Continent = Continent.EUROPE
        override val nations: List<Nation> = listOf(
            Nation(capitals = capitals(Capital(capital = R.string.capital_athens)), country = R.string.country_greece, key = "그리스"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_amsterdam)), country = R.string.country_netherlands, key = "네덜란드"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_oslo)), country = R.string.country_norway, key = "노르웨이"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_copenhagen)), country = R.string.country_denmark, key = "덴마크"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_berlin)), country = R.string.country_germany, key = "독일"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_riga)), country = R.string.country_latvia, key = "라트비아"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_moscow)), country = R.string.country_russia, key = "러시아"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_bucharest)), country = R.string.country_romania, key = "루마니아"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_luxembourg)), country = R.string.country_luxembourg, key = "룩셈부르크"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_vilnius)), country = R.string.country_lithuania, key = "리투아니아"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_vaduz)), country = R.string.country_liechtenstein, key = "리히텐슈타인"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_monaco)), country = R.string.country_monaco, key = "모나코"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_podgorica)), country = R.string.country_montenegro, key = "몬테네그로"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_chisinau)), country = R.string.country_moldova, key = "몰도바"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_valletta)), country = R.string.country_malta, key = "몰타"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_vatican_city)), country = R.string.country_vatican_city, key = "바티칸 시국"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_skopje)), country = R.string.country_north_macedonia, key = "북마케도니아"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_brussels)), country = R.string.country_belgium, key = "벨기에"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_minsk)), country = R.string.country_belarus, key = "벨라루스"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_sarajevo)), country = R.string.country_bosnia_and_herzegovina, key = "보스니아 헤르체고비나"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_sofia)), country = R.string.country_bulgaria, key = "불가리아"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_san_marino)), country = R.string.country_san_marino, key = "산마리노"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_belgrade)), country = R.string.country_serbia, key = "세르비아"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_stockholm)), country = R.string.country_sweden, key = "스웨덴"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_bern)), country = R.string.country_switzerland, key = "스위스"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_madrid)), country = R.string.country_spain, key = "스페인"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_bratislava)), country = R.string.country_slovakia, key = "슬로바키아"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_ljubljana)), country = R.string.country_slovenia, key = "슬로베니아"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_reykjavik)), country = R.string.country_iceland, key = "아이슬란드"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_dublin)), country = R.string.country_ireland, key = "아일랜드"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_andorra_la_vella)), country = R.string.country_andorra, key = "안도라"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_tirana)), country = R.string.country_albania, key = "알바니아"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_tallinn)), country = R.string.country_estonia, key = "에스토니아"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_london)), country = R.string.country_united_kingdom, key = "영국"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_vienna)), country = R.string.country_austria, key = "오스트리아"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_kiev)), country = R.string.country_ukraine, key = "우크라이나"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_cardiff)), country = R.string.country_wales, key = "웨일스"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_rome)), country = R.string.country_italy, key = "이탈리아"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_tbilisi)), country = R.string.country_georgia, key = "조지아"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_prague)), country = R.string.country_czech_republic, key = "체코"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_pristina)), country = R.string.country_kosovo, key = "코소보"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_zagreb)), country = R.string.country_croatia, key = "크로아티아"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_nicosia)), country = R.string.country_cyprus, key = "키프로스"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_ankara)), country = R.string.country_turkeye, key = "튀르키예"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_lisbon)), country = R.string.country_portugal, key = "포르투갈"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_warsaw)), country = R.string.country_poland, key = "폴란드"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_paris)), country = R.string.country_france, key = "프랑스"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_helsinki)), country = R.string.country_finland, key = "핀란드"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_budapest)), country = R.string.country_hungary, key = "헝가리"),
        )

        override val ordinal: Int = TWO
    }

    object Oceania : WorldCapitals {
        override val continent: Continent = Continent.OCEANIA
        override val nations: List<Nation> = listOf(
            Nation(capitals = capitals(Capital(capital = R.string.capital_hagatna)), country = R.string.country_guam, key = "괌"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_yaren)), country = R.string.country_nauru, key = "나우루"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_wellington)), country = R.string.country_new_zealand, key = "뉴질랜드"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_alofi)), country = R.string.country_niue, key = "니우에"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_majuro)), country = R.string.country_marshall_islands, key = "마셜 제도"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_palikir)), country = R.string.country_micronesia, key = "미크로네시아 연방"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_port_vila)), country = R.string.country_vanuatu, key = "바누아투"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_saipan)), country = R.string.country_northern_mariana_islands, key = "북마리아나 제도"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_apia)), country = R.string.country_samoa, key = "사모아"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_honiara)), country = R.string.country_solomon_islands, key = "솔로몬 제도"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_canberra)), country = R.string.country_australia, key = "오스트레일리아"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_avarua)), country = R.string.country_cook_islands, key = "쿡 제도"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_tarawa)), country = R.string.country_kiribati, key = "키리바시"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_nuku_alofa)), country = R.string.country_tonga, key = "통가"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_funafuti)), country = R.string.country_tuvalu, key = "투발루"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_port_moresby)), country = R.string.country_papua_new_guinea, key = "파푸아뉴기니"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_ngeulmud)), country = R.string.country_palau, key = "팔라우"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_suva)), country = R.string.country_fiji, key = "피지"),
        )

        override val ordinal: Int = THREE
    }

    object Africa : WorldCapitals {
        override val continent: Continent = Continent.AFRICA
        override val nations: List<Nation> = listOf(
            Nation(capitals = capitals(Capital(capital = R.string.capital_accra)), country = R.string.country_ghana, key = "가나"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_libreville)), country = R.string.country_gabon, key = "가봉"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_banjul)), country = R.string.country_gambia, key = "감비아"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_bissau)), country = R.string.country_guinea_bissau, key = "기니비사우"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_windhoek)), country = R.string.country_namibia, key = "나미비아"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_abuja)), country = R.string.country_nigeria, key = "나이지리아"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_juba)), country = R.string.country_south_sudan, key = "남수단"),
            Nation(
                capitals = capitals(
                    Capital(capital = R.string.capital_pretoria, role = Role.ADMINISTRATIVE),
                    Capital(capital = R.string.capital_bloemfontein, role = Role.JUDICIAL),
                    Capital(capital = R.string.capital_capetown, role = Role.LEGISLATIVE),
                ),
                country = R.string.country_south_africa,
                key = "남아프리카 공화국",
            ),
            Nation(capitals = capitals(Capital(capital = R.string.capital_niamey)), country = R.string.country_niger, key = "니제르"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_monrovia)), country = R.string.country_liberia, key = "라이베리아"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_maseru)), country = R.string.country_lesotho, key = "레소토"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_kigali)), country = R.string.country_rwanda, key = "르완다"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_tripoli)), country = R.string.country_libya, key = "리비아"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_antananarivo)), country = R.string.country_madagascar, key = "마다가스카르"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_lilongwe)), country = R.string.country_malawi, key = "말라위"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_bamako)), country = R.string.country_mali, key = "말리"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_rabat)), country = R.string.country_morocco, key = "모로코"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_port_louis)), country = R.string.country_mauritius, key = "모리셔스"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_nouakchott)), country = R.string.country_mauritania, key = "모리타니"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_maputo)), country = R.string.country_mozambique, key = "모잠비크"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_porto_novo)), country = R.string.country_benin, key = "베냉"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_gaborone)), country = R.string.country_botswana, key = "보츠와나"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_gitega)), country = R.string.country_burundi, key = "부룬디"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_ouagadougou)), country = R.string.country_burkina_faso, key = "부르키나파소"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_sao_tome)), country = R.string.country_sao_tome_and_principe, key = "상투메 프린시페"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_dakar)), country = R.string.country_senegal, key = "세네갈"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_victoria)), country = R.string.country_seychelles, key = "세이셸"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_mogadishu)), country = R.string.country_somalia, key = "소말리아"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_khartoum)), country = R.string.country_sudan, key = "수단"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_freetown)), country = R.string.country_sierra_leone, key = "시에라리온"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_algiers)), country = R.string.country_algeria, key = "알제리"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_luanda)), country = R.string.country_angola, key = "앙골라"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_asmara)), country = R.string.country_eritrea, key = "에리트레아"),
            Nation(
                capitals = capitals(
                    Capital(capital = R.string.capital_mbabane, role = Role.ADMINISTRATIVE),
                    Capital(capital = R.string.capital_lobamba, role = Role.LEGISLATIVE),
                ),
                country = R.string.country_eswatini,
                key = "에스와티니",
            ),
            Nation(capitals = capitals(Capital(capital = R.string.capital_addis_ababa)), country = R.string.country_ethiopia, key = "에티오피아"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_kampala)), country = R.string.country_uganda, key = "우간다"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_cairo)), country = R.string.country_egypt, key = "이집트"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_lusaka)), country = R.string.country_zambia, key = "잠비아"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_malabo)), country = R.string.country_equatorial_guinea, key = "적도 기니"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_bangui)), country = R.string.country_central_african_republic, key = "중앙아프리카 공화국"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_djibouti)), country = R.string.country_djibouti, key = "지부티"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_harare)), country = R.string.country_zimbabwe, key = "짐바브웨"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_ndjamena)), country = R.string.country_chad, key = "차드"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_yaounde)), country = R.string.country_cameroon, key = "카메룬"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_praia)), country = R.string.country_cape_verde, key = "카보베르데"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_nairobi)), country = R.string.country_kenya, key = "케냐"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_moroni)), country = R.string.country_comoros, key = "코모로"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_yamoussoukro)), country = R.string.country_cote_divoire, key = "코트디부아르"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_brazzaville)), country = R.string.country_republic_of_the_congo, key = "콩고 공화국"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_kinshasa)), country = R.string.country_democratic_republic_of_the_congo, key = "콩고 민주 공화국"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_dodoma)), country = R.string.country_tanzania, key = "탄자니아"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_lome)), country = R.string.country_togo, key = "토고"),
            Nation(capitals = capitals(Capital(capital = R.string.capital_tunis)), country = R.string.country_tunisia, key = "튀니지"),
        )

        override val ordinal: Int = FOUR
    }
}
