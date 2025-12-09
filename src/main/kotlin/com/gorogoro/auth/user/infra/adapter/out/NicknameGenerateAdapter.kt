package com.gorogoro.auth.user.infra.adapter.out

import com.gorogoro.auth.user.application.port.out.NicknamePolicyPort
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class NicknameGenerateAdapter : NicknamePolicyPort {
    val prefixes = listOf(
        "즐거운", "행복한", "신나는", "용감한", "씩씩한",
        "상냥한", "차분한", "열정적인", "수줍은", "호기심많은",
        "꿈꾸는", "자유로운", "긍정적인", "수상한", "솔직한",
        "배고픈", "졸린", "바쁜", "심심한", "게으른",
        "부지런한", "달리는", "노래하는", "춤추는", "책읽는",
        "생각하는", "여행하는", "운동하는", "잠자는", "떠나는",
        "빨간", "파란", "노란", "하얀", "검은",
        "투명한", "반짝이는", "화려한", "소박한", "깨끗한",
        "귀여운", "멋진", "이상한", "특별한", "평범한",
        "튼튼한", "말랑한", "따뜻한", "시원한", "달콤한"
    )
    val suffixes = listOf(
        "강아지", "고양이", "토끼", "다람쥐", "병아리",
        "호랑이", "사자", "곰돌이", "판다", "펭귄",
        "거북이", "돌고래", "부엉이", "독수리", "알파카",
        "사과", "복숭아", "감자", "고구마", "옥수수",
        "바게트", "도넛", "푸딩", "젤리", "커피",
        "하늘", "구름", "바람", "이슬", "무지개",
        "별똥별", "선인장", "나무", "꽃송이", "조약돌",
        "자전거", "비행기", "로봇", "눈사람", "그림자",
        "탐험가", "여행자", "학습자", "선생님", "학생",
        "히어로", "마법사", "요정", "예술가", "박사님"
    )

    override fun generate(): String {
        val prefix = prefixes.random()
        val suffix = suffixes.random()

        val number = Random.nextInt(1000, 10000)

        return "${prefix} ${suffix}${number}"
    }
}
