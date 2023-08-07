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
    var size: CGFloat? = nil
    
    var body: some View {
        Text(text)
            .font(font)
            .foregroundColor(color)
    }
    
    var font : Font {
        switch type {
        case .heading:
            return .spaceGrotesk(withStyle: .largeTitle, size: size ?? 48)
        case .subHeading:
            return .spaceGrotesk(withStyle: .headline, size: size ?? 28)
        case .title:
            return .urbanist(withStyle: .title, size: size ?? 24)
        case .subTitle:
            return .urbanist(withStyle: .title3, size: size ?? 18)
        case .label:
            return .urbanist(withStyle: .body, size: size ?? 16)
        case .mediumLabel:
            return .urbanist(withStyle: .title2, size: size ?? 16)
        case .smallLabel:
            return .urbanist(withStyle: .caption, size: size ?? 12)
        case .custom(let font, let style, let size):
            switch font {
            case .urbanist:
                return .urbanist(withStyle: style, size: size)
            case .spaceGrotesk:
                return .spaceGrotesk(withStyle: style, size: size)
            }
        }
    }
}

enum ZiuqTextType {
    case heading
    case subHeading
    case title
    case subTitle
    case label
    case mediumLabel
    case smallLabel
    case custom(ZiuqFont,Font.TextStyle,CGFloat)
}

enum ZiuqFont {
    case urbanist
    case spaceGrotesk
}

struct ZiuqText_Previews: PreviewProvider {
    static var previews: some View {
        VStack {
            ZiuqText(text: "Click Here", type: .heading)
            ZiuqText(text: "Click Here", type: .subHeading)
            ZiuqText(text: "Click Here", type: .title)
            ZiuqText(text: "Click Here", type: .subTitle)
            ZiuqText(text: "Click Here", type: .label)
            ZiuqText(text: "Click Here", type: .mediumLabel)
            ZiuqText(text: "Click Here", type: .smallLabel)
            ZiuqText(text: "Click Here", type: .custom(.spaceGrotesk, .largeTitle, 66))
        }
        .showAllPreviewTypes()
    }
}
