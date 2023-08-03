//
//  Font+Extensions.swift
//  iosApp
//
//  Created by Norris Aboagye on 01/08/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

extension Font {
    
    static func urbanist(
        withStyle style: Font.TextStyle = .body,
        size: CGFloat = 16.0
    ) -> Font {
        switch style {
        case .largeTitle:
            return Font(Fonts().urbanist.extraBold.uiFont(withSize: size))
        case .title:
            return Font(Fonts().urbanist.light.uiFont(withSize: size))
        case .title2:
            return Font(Fonts().urbanist.bold.uiFont(withSize: size))
        case .title3:
            return Font(Fonts().urbanist.bold.uiFont(withSize: size))
        case .headline:
            return Font(Fonts().urbanist.semiBold.uiFont(withSize: size))
        case .subheadline:
            return Font(Fonts().urbanist.medium.uiFont(withSize: size))
        case .body:
            return Font(Fonts().urbanist.regular.uiFont(withSize: size))
        case .callout:
            return Font(Fonts().urbanist.light.uiFont(withSize: size))
        case .footnote:
            return Font(Fonts().urbanist.extraLight.uiFont(withSize: size))
        case .caption:
            return Font(Fonts().urbanist.thin.uiFont(withSize: size))
        case .caption2:
            return Font(Fonts().urbanist.thin.uiFont(withSize: size))
        @unknown default:
            return Font(Fonts().urbanist.regular.uiFont(withSize: size))
        }
    }
    
    static func spaceGrotesk(
        withStyle style: Font.TextStyle = .body,
        size: CGFloat = 16.0
    ) -> Font {
        switch style {
        case .largeTitle:
            return Font(Fonts().spaceGrotesk.bold.uiFont(withSize: size))
        case .title:
            return Font(Fonts().spaceGrotesk.semiBold.uiFont(withSize: size))
        case .title2:
            return Font(Fonts().spaceGrotesk.bold.uiFont(withSize: size))
        case .title3:
            return Font(Fonts().spaceGrotesk.bold.uiFont(withSize: size))
        case .headline:
            return Font(Fonts().spaceGrotesk.semiBold.uiFont(withSize: size))
        case .subheadline:
            return Font(Fonts().spaceGrotesk.medium.uiFont(withSize: size))
        case .body:
            return Font(Fonts().spaceGrotesk.regular.uiFont(withSize: size))
        case .callout:
            return Font(Fonts().spaceGrotesk.light.uiFont(withSize: size))
        case .footnote:
            return Font(Fonts().spaceGrotesk.light.uiFont(withSize: size))
        case .caption:
            return Font(Fonts().spaceGrotesk.light.uiFont(withSize: size))
        case .caption2:
            return Font(Fonts().spaceGrotesk.light.uiFont(withSize: size))
        @unknown default:
            return Font(Fonts().spaceGrotesk.regular.uiFont(withSize: size))
        }
    }
    
}
