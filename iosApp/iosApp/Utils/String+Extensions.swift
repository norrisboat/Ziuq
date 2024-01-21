//
//  String+Extensions.swift
//  iosApp
//
//  Created by Norris Aboagye on 11/01/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation


extension String {
    var toUrl: URL? {
        URL(string: self)
    }
    
    //To check text field or String is blank or not
    var isBlank: Bool {
        get {
            let trimmed = trimmingCharacters(in: CharacterSet.whitespaces)
            return trimmed.isEmpty
        }
    }
    
    var isNotBlank: Bool {
        get {
            return !self.isBlank
        }
    }
}
