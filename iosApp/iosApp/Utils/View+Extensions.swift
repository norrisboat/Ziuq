//
//  View+Extensions.swift
//  iosApp
//
//  Created by Norris Aboagye on 01/08/2023.
//  Copyright © 2023 orgName. All rights reserved.
//


import SwiftUI

extension View {
	
	func fillMaxSize(alignment: Alignment = .center) -> some View {
		self.frame(maxWidth: .infinity, maxHeight: .infinity, alignment: alignment)
	}
    
    func fillWidth(alignment: Alignment = .center) -> some View {
        self.frame(maxWidth: .infinity, alignment: alignment)
    }
    
    func showAllPreviewTypes(previewLayout: PreviewLayout = .sizeThatFits) -> some View {
        ForEach(ColorScheme.allCases, id: \.self) {
            self.preferredColorScheme($0).previewLayout(previewLayout)
        }
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
