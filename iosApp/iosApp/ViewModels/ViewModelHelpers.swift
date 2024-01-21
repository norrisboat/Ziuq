//
//  ViewModelHelpers.swift
//  iosApp
//
//  Created by Norris Aboagye on 14/01/2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared

typealias Collector = Kotlinx_coroutines_coreFlowCollector

class Observer: Collector {
    func emit(value: Any?, completionHandler: @escaping (Error?) -> Void) {
        callback(value)
        completionHandler(nil)
    }
    
    let callback:(Any?) -> Void
    
    init(callback: @escaping (Any?) -> Void) {
        self.callback = callback
    }
}

class FlowCollector<T>: Collector {
    let callback: (T) -> Void

    init(callback: @escaping (T) -> Void) {
        self.callback = callback
    }

    func emit(value: Any?, completionHandler: @escaping (Error?) -> Void) {
        callback(value as! T)
        completionHandler(nil)
    }
}
