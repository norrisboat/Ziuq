//
//  View+Extensions.swift
//  iosApp
//
//  Created by Norris Aboagye on 01/08/2023.
//  Copyright © 2023 orgName. All rights reserved.
//


import SwiftUI
import shared

extension View {
	
	func fillMaxSize(alignment: Alignment = .center) -> some View {
		self.frame(maxWidth: .infinity, maxHeight: .infinity, alignment: alignment)
	}
    
    func fillWidth(alignment: Alignment = .center) -> some View {
        self.frame(maxWidth: .infinity, alignment: alignment)
    }
    
    func size(of size: CGFloat, alignment: Alignment = .center) -> some View {
        self.frame(width: size, height: size, alignment: alignment)
    }
    
    func sidePadding(padding: CGFloat = 20.0) -> some View {
        self.padding(.leading, padding).padding(.trailing, padding)
    }
    
    func showAllPreviewTypes(previewLayout: PreviewLayout = .sizeThatFits) -> some View {
        ForEach(ColorScheme.allCases, id: \.self) {
            self.preferredColorScheme($0).previewLayout(previewLayout)
        }
    }
    
    func makeButton(padding: CGFloat = 0.0, role: ButtonRole? = nil, traits: AccessibilityTraits = .isButton, animation: Animation? = nil, hoverEffect: HoverEffect? = .automatic, action: @escaping () -> Void) -> some View {
        Button(
            role: role,
            action: {
                if animation != nil { withAnimation(animation) { action() } }
                else { action() }
            },
            label: {
                if let hoverEffect = hoverEffect {
                    self.padding(padding).hoverEffect(hoverEffect)
                } else {
                    self.padding(padding)
                }
            }
        )
        .accessibilityRemoveTraits(.isButton)
        .accessibilityAddTraits(traits)
        .padding(-padding)
    }
    
    @ViewBuilder
    func `if`<Transform: View>(_ condition: Bool, transform: (Self) ->  Transform) -> some View {
        if condition { transform(self) }
        else { self }
    }
    
    @ViewBuilder
    func `if`(_ condition: Bool) -> some View {
        // Use of @ViewBuilder and `if` here are intentional. `condition ? self : nil` doesn't work the same.
        if condition {
            self
        }
    }
	
}

extension Image {
    init(resource: ResourcesImageResource) {
        self.init(uiImage: resource.toUIImage()!)
    }
}
