//
//  ZiuqText.swift
//  iosApp
//
//  Created by Norris Aboagye on 01/08/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct ZiuqText: View {
    var text: String
    var type: ZiuqTextType
    var color: Color = .label
    
    var body: some View {
        Text(text)
            .font(font)
            .foregroundColor(color)
    }
    
    var font : Font {
        switch type {
        case .heading:
            return .spaceGrotesk(withStyle: .largeTitle, size: 48)
        case .subHeading:
            return .spaceGrotesk(withStyle: .headline, size: 28)
        case .title:
            return .urbanist(withStyle: .title, size: 24)
        case .subTitle:
            return .urbanist(withStyle: .title3, size: 18)
        case .label:
            return .urbanist(withStyle: .body)
        case .mediumLabel:
            return .urbanist(withStyle: .title2)
        case .smallLabel:
            return .urbanist(withStyle: .caption, size: 12)
        case .custom:
            return .urbanist(withStyle: .body, size: 14)
        }
    }
}

enum ZiuqTextType : CaseIterable {
    case heading
    case subHeading
    case title
    case subTitle
    case label
    case mediumLabel
    case smallLabel
    case custom
}

struct ZiuqText_Previews: PreviewProvider {
    static var previews: some View {
        VStack {
            ForEach(ZiuqTextType.allCases, id: \.self) { type in
                ZiuqText(text: "Click Here", type: type)
            }
        }
        .showAllPreviewTypes()
    }
}
